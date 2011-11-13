package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileGenerator {
	
	private static final String MAVEN_PACKAGE_JAVA = "src/main/java/";
	
	private static final String MAVEN_PACKAGE_WEBAPP = "src/main/webapp/";
	
	private final String package_root;
	
	private String package_java;
	
	private String package_webapp;
	
	public FileGenerator(String package_root) {
		this.package_root = package_root;
		usingSource(MAVEN_PACKAGE_JAVA).usingWebapp(MAVEN_PACKAGE_WEBAPP);
	}
	
	public FileGenerator usingSource(String javasource) {
		javasource += package_root;
		this.package_java = javasource.replaceAll("\\.", "/") + "/";
		return this;
	}
	
	public FileGenerator usingWebapp(String webapp) {
		this.package_webapp = webapp;
		return this;
	}	
	
	public boolean generateModel(String modelname) throws Exception {
		

		File file = createFileFromTemplate(modelname, modelname + ".java", package_java + "model", "Model.tpl");
		
		file = createFileFromTemplate(modelname + "DAO", modelname + "DAO.java", package_java + "dao", "DAO.tpl");
		
		return file.exists();
	}

	private File createFileFromTemplate(String modelname, String filename, String subfolder, String template) throws Exception {
		InputStream is = readTemplate(template);
		
		String modelStr = this.generateSource(is, modelname);
		
		return createSource(filename, subfolder, modelStr);
	}

	
	private File createSource(String filename, String subfolder, String content) throws Exception {
		subfolder += "/";
		FileSystem.createFolder(subfolder);

		File newFile = FileSystem.writeNewFile(subfolder + filename);
		File file = this.saveToFile(newFile, content.getBytes());
		
		if (!file.exists()) {
			throw new Exception("Error creating file");
		}
		
		return file;
	}
	
	public boolean generateController(String modelname, String method) throws Exception {
		
		File file = createFileFromTemplate(modelname, modelname + "Controller.java", package_java + "controller", "Controller_" + method + ".tpl");
	
		String modelname_lowercase = modelname.toLowerCase();
		
		file = createFileFromTemplate(modelname, method + ".jsp", package_webapp + "WEB-INF/jsp/" + modelname_lowercase, "JSP_" + method + ".tpl");
		
		file = updateFileFromTemplate(modelname, package_webapp + "index.jsp", "Index_" + method + ".tpl");
		
		return file.exists();
	}	
	
	private File updateFileFromTemplate(String modelname, String filename, String template) throws Exception {
		InputStream is = readTemplate(template);
		String modelStr = this.generateSource(is, modelname);
		
		File newFile = FileSystem.read(filename);
		String index = FileSystem.readContent(newFile);
		
		int pos = index.lastIndexOf("</ul>");
		
		String new_content = index.substring(0, pos) + modelStr + index.substring(pos, index.length());
		
		File file = this.saveToFile(newFile, new_content.getBytes());		
		return file;
	}
	
	private InputStream readTemplate(String filename) {
		InputStream is = FileGenerator.class.getClassLoader().getResourceAsStream(filename);
		return is;
	}
	
	
	private String generateSource(InputStream is, String modelname) {
		
		String source = "";
		
        try {
        	
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.available() > 0) {
                baos.write(is.read());
            }
            
            source = baos.toString();
            is.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        StringBuffer code = new StringBuffer(source);
        replace(code, "%PACKAGE%", package_root);
        replace(code, "%MODELNAME%", modelname);
        replace(code, "%MODELNAME_LOWERCASE%", modelname.toLowerCase());
		
		return code.toString();
		
	}	
	
	
	
	
	
	
	
	public File saveToFile(File filename, byte[] content) throws IOException {
		
		ByteArrayInputStream is = new ByteArrayInputStream(content);
		File outputfile = filename;
		FileOutputStream out;
		
		try {
			
			out = new FileOutputStream(outputfile);
			byte buf[]=new byte[1024];
			int len;

			while ((len=is.read(buf))>0)
				out.write(buf,0,len);
			
			out.close();
			is.close();
			
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		
		return outputfile;
	}	
	
    public static void replace(StringBuffer code, String marker, String newCode) {
        int posMarker = code.indexOf(marker);
        while (posMarker >= 0) {
        	code.replace(posMarker, posMarker + marker.length(), newCode);
        	posMarker = code.indexOf(marker);
        }
    }
    
}
