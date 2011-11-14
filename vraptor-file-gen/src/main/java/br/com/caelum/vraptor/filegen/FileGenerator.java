package br.com.caelum.vraptor.filegen;

import java.io.File;

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
		
		Source source = new Source(modelname);
		source.setPackage(package_root);
		source.setExtension("java");
		source.usingTemplate(template).generateSource().savenewfileTo(package_java + "model");
		
		template = new Template("DAO.tpl");
		
		source = new Source(modelname + "DAO");
		source.setPackage(package_root);
		source.setExtension("java");
		source.setModelname(modelname);
		source.usingTemplate(template).generateSource().savenewfileTo(package_java + "dao");
		
		return true;
	}
	
	public boolean generateController(String modelname, String method) throws Exception {

		Template template = new Template("Controller_" + method + ".tpl");
		
		Source source = new Source(modelname + "Controller");
		source.setPackage(package_root);
		source.setExtension("java");
		source.setModelname(modelname);
		source.usingTemplate(template).generateSource().savenewfileTo(package_java + "controller");
		
		template = new Template("JSP_" + method + ".tpl");

		String modelname_lowercase = modelname.toLowerCase();
		source = new Source(method);
		source.setExtension("jsp");
		source.setModelname(modelname);
		source.usingTemplate(template).generateSource().savenewfileTo(package_webapp + "WEB-INF/jsp/" + modelname_lowercase);
		
		template = new Template("Index_" + method + ".tpl");
		
		source = new Source("index");
		source.setModelname(modelname);
		source.usingTemplate(template).generateSource();
		
		File existingIndex = FileSystem.read(package_webapp + "index.jsp");
		
		Source new_index = new Source(existingIndex);
		new_index.updateSourceBefore("</ul>", source.getContent());
		new_index.savefile();
		
		return true;
	}	
	
}
