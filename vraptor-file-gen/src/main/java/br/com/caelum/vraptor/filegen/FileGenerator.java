package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
		
		Template template = new Template("Model.tpl");
		
		Source source = new Source(modelname, package_root);
		source.usingTemplate(template).generateSource();
		
		String filename = modelname + ".java";
		String subfolder = package_java + "model";
		
		File file = createSource(filename, subfolder, source.getContent());
		
		template = new Template("DAO.tpl");
		source = new Source(modelname, package_root);
		source.usingTemplate(template).generateSource();
		
		filename = modelname + "DAO.java";
		subfolder = package_java + "dao";
		file = createSource(filename, subfolder, source.getContent());
		
		return file.exists();
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

		Template template = new Template("Controller_" + method + ".tpl");
		
		Source source = new Source(modelname, package_root);
		source.usingTemplate(template).generateSource();
		
		String filename = modelname + "Controller.java";
		String subfolder = package_java + "controller";
		File file = createSource(filename, subfolder, source.getContent());
	

		template = new Template("JSP_" + method + ".tpl");

		source = new Source(method, package_webapp);
		source.usingTemplate(template).generateSource();
		
		String modelname_lowercase = modelname.toLowerCase();
		filename = method + ".jsp";
		subfolder = package_webapp + "WEB-INF/jsp/" + modelname_lowercase;
		file = createSource(filename, subfolder, source.getContent());
		
		file = updateFileFromTemplate(modelname, package_webapp + "index.jsp", "Index_" + method + ".tpl");
		
		return file.exists();
	}	
	
	private File updateFileFromTemplate(String modelname, String filename, String template) throws Exception {

		Template temp = new Template(template);
		Source source = new Source(modelname, "");
		source.usingTemplate(temp).generateSource();
		
		File newFile = FileSystem.read(filename);
		String index = FileSystem.readContent(newFile);
		
		int pos = index.lastIndexOf("</ul>");
		
		String new_content = index.substring(0, pos) + source.getContent() + index.substring(pos, index.length());
		
		File file = this.saveToFile(newFile, new_content.getBytes());		
		return file;
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
	
    
}
