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
	
	//public static String package_jsp = package_web + "WEB-INF/jsp/";
	
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
		

		File file = createFileFromTemplate(modelname, "model", "Model.tpl");
		
		file = createFileFromTemplate(modelname + "DAO", "dao", "DAO.tpl");
		
		return file.exists();
	}

	private File createFileFromTemplate(String modelname, String subfolder, String template) throws Exception {
		
		subfolder += "/";
		FileSystem.createFolder(package_java + subfolder);
		
		InputStream is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		String modelStr = this.generateSource(is, modelname);
		
		File newFile = FileSystem.writeNewFile(package_java + subfolder + modelname + ".java");
		File file = this.saveToFile(newFile, modelStr.getBytes());
		
		if (!file.exists()) {
			throw new Exception("Error creating file");
		}
		
		return file;
	}
	
	
	public boolean generateController(String modelname, String method) throws Exception {
		
		String modelname_lowercase = modelname.toLowerCase();
		
		FileSystem.createFolder(package_java + "controller");
		FileSystem.createFolder(package_webapp + "WEB-INF/jsp/" + modelname_lowercase);
		
		
		String template = "Controller_" + method + ".tpl";
		InputStream is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		String modelStr = this.generateSource(is, modelname);
		
		File newFile = FileSystem.writeNewFile(package_java + "controller/" + modelname + "Controller.java");
		File file = this.saveToFile(newFile, modelStr.getBytes());
		
		if (!file.exists()) {
			throw new Exception("Error creating file");
		}
		
		template = "JSP_" + method + ".tpl";
		is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		modelStr = this.generateSource(is, modelname);
		newFile = FileSystem.writeNewFile(package_webapp + "WEB-INF/jsp/" + modelname_lowercase + "/" + method + ".jsp");
		file = this.saveToFile(newFile, modelStr.getBytes());
		
		template = "Index_" + method + ".tpl";
		is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		modelStr = this.generateSource(is, modelname);
		
		newFile = FileSystem.read(package_webapp + "index.jsp");
		String index = FileSystem.readContent(newFile);
		
		int pos = index.lastIndexOf("</ul>");
		
		String new_content = index.substring(0, pos) + modelStr + index.substring(pos, index.length());
		
		file = this.saveToFile(newFile, new_content.getBytes());		
		
		
		
		return file.exists();
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
