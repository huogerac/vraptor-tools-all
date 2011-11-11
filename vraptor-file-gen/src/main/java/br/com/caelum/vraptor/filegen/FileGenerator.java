package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileGenerator {
	
	private final String package_root;
	
	private String package_java;
	
	public static String package_web = "src/main/webapp/";
	
	public static String package_jsp = package_web + "WEB-INF/jsp/";
	
	public FileGenerator(String package_root) {
		this.package_root = package_root;
		this.package_java = "src/main/java/" + package_root.replaceAll("\\.", "/") + "/";
	}
	
	
	public boolean generateModel(String modelname) throws Exception {
		
		FileSystem.createFolder(package_java + "model");
		FileSystem.createFolder(package_java + "dao");

		String template = "Model.tpl";
		InputStream is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		String modelStr = this.generateSource(is, modelname);
		
		File newFile = FileSystem.writeNewFile(package_java + "model/" + modelname + ".java");
		File file = this.saveToFile(newFile, modelStr.getBytes());
		
		if (!file.exists()) {
			throw new Exception("Error creating file");
		}
		
		template = "DAO.tpl";
		is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		modelStr = this.generateSource(is, modelname);
		newFile = FileSystem.writeNewFile(package_java + "dao/" + modelname + "DAO.java");
		file = this.saveToFile(newFile, modelStr.getBytes());		
		
		return file.exists();
	}
	
	
	public boolean generateController(String modelname, String method) throws Exception {
		
		String modelname_lowercase = modelname.toLowerCase();
		
		FileSystem.createFolder(package_java + "controller");
		FileSystem.createFolder(package_jsp + modelname_lowercase);
		
		
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
		newFile = FileSystem.writeNewFile(package_jsp + modelname_lowercase + "/" + method + ".jsp");
		file = this.saveToFile(newFile, modelStr.getBytes());
		
		template = "Index_" + method + ".tpl";
		is = FileGenerator.class.getClassLoader().getResourceAsStream(template);
		
		modelStr = this.generateSource(is, modelname);
		
		newFile = FileSystem.read(package_web + "index.jsp");
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
