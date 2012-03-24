package br.com.caelum.vraptor.filegen;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
	
	private static final String MAVEN_PACKAGE_JAVA = "src/main/java/";
	
	private static final String MAVEN_PACKAGE_WEBAPP = "src/main/webapp/";
	
	private static final String MAVEN_PACKAGE_RESOURCES = "src/main/resources/";
	
	private final String package_root;
	
	private String package_java;
	
	private String package_webapp;
	
	private String package_resources;
	
	private String persistenceAPI;
	
	private final String modelname;
	
	private final String fields;

	private final String repository;
	
	
	public static final String HIBERNATE = "hibernate";
	public static final String MONGODB = "mongodb";
	
	public FileGenerator(String modelname, String fields, String repository, String package_root) {
		this.modelname = modelname;
		this.fields = fields;
		this.repository = repository;
		this.package_root = package_root;
		usingSource(MAVEN_PACKAGE_JAVA).usingWebapp(MAVEN_PACKAGE_WEBAPP).usingResources(MAVEN_PACKAGE_RESOURCES);
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
	
	public FileGenerator usingResources(String resources) {
		this.package_resources = resources;
		return this;
	}
	
	public FileGenerator toPersistenceAPI(String persistenceAPI) {
		this.persistenceAPI = persistenceAPI;
		return this;
	}
	
	public boolean generateModel() throws Exception {
		
		Source modelfile = generateSourceFromTemplate(modelname, "java", 
				persistenceAPI + "/Model.tpl", 
				persistenceAPI + "/Model_fields.tpl");
		modelfile.savenewfileTo(package_java + "model");

		Source repositoryfile = generateSourceFromTemplate(repository, "java", 
				persistenceAPI + "/Repository.tpl", 
				null);
		repositoryfile.savenewfileTo(package_java + "repository");
		
		
		Source daofile = generateSourceFromTemplate(modelname + "DAO", "java", 
				persistenceAPI + "/DAO.tpl", 
				null);
		daofile.savenewfileTo(package_java + "repository/impl");

		
		Source hibernatefile = updateSourceFromTemplate(package_resources + "hibernate.cfg", "xml",
				persistenceAPI + "/HibernateCfg.tpl");
		hibernatefile.addContentIn(".+</session-factory>").savefile();
		
		return true;
	}

	private Source generateSourceFromTemplate(String filename, String extension, String templatename, String templatefieldsname) throws Exception {
		
		Source source = new Source(filename);
		source.setExtension(extension);
		source.setPackage(package_root);
		source.setModelname(modelname);
		source.setRepositoryname(repository);
		
		Template template = new Template(templatename);
		
		if (templatefieldsname != null) {
			Template templatefields = new Template(templatefieldsname);
			source.addFieldsTemplate( templatefields );
			
			List<ModelField> modelFields = loadFields(fields);
			source.setModelFields(modelFields);
		}
		source.usingTemplate(template).generateSource(); //.savenewfileTo(package_java + "model");
		
		return source;
	}
	

	private Source updateSourceFromTemplate(String filename, String extension,
			String templatename) throws Exception {

		Source source = new Source(filename);
		source.setExtension(extension);
		source.setPackage(package_root);
		source.setModelname(modelname);
		source.setRepositoryname(repository);
		
		Template template = new Template(templatename);
		source.usingTemplate(template).withThisSourceFile();
		
		return source;
	}
	
	
	public boolean generateController(String modelname, String repository, String fields, String method) throws Exception {

		List<ModelField> modelFields = loadFields(fields);
		
		Template template = new Template(persistenceAPI + "/Controller_" + method + ".tpl");
		
		Source source = new Source(repository + "Controller");
		source.setPackage(package_root);
		source.setExtension("java");
		source.setModelname(modelname);
		source.setRepositoryname(repository);
		source.usingTemplate(template).generateSource().savenewfileTo(package_java + "controller");
		
		
		template = new Template(persistenceAPI + "/Jsp_formulario_" + method + ".tpl");
		
		String repositoryname_lowercase = repository.toLowerCase();
		source = new Source("formulario");
		source.setExtension("jsp");
		source.setModelname(modelname);
		source.setModelFields(modelFields);
		source.setRepositoryname(repository);
		source.addFieldsTemplate( new Template(persistenceAPI + "/Jsp_formulario_fields_" + method + ".tpl") );
		source.usingTemplate(template).generateSource().savenewfileTo(package_webapp + "WEB-INF/jsp/" + repositoryname_lowercase);


		template = new Template(persistenceAPI + "/Jsp_edita_" + method + ".tpl");
		
		source = new Source("edita");
		source.setExtension("jsp");
		source.setModelname(modelname);
		source.setModelFields(modelFields);
		source.setRepositoryname(repository);
		source.addFieldsTemplate( new Template(persistenceAPI + "/Jsp_edita_fields_" + method + ".tpl") );
		source.usingTemplate(template).generateSource().savenewfileTo(package_webapp + "WEB-INF/jsp/" + repositoryname_lowercase);

		
		template = new Template(persistenceAPI + "/Jsp_lista_" + method + ".tpl");
		
		source = new Source("lista");
		source.setExtension("jsp");
		source.setModelname(modelname);
		source.setModelFields(modelFields);
		source.setRepositoryname(repository);
		source.addFieldsTemplate( new Template(persistenceAPI + "/Jsp_lista_fields_" + method + ".tpl") );
		source.usingTemplate(template).generateSource().savenewfileTo(package_webapp + "WEB-INF/jsp/" + repositoryname_lowercase);
		
		
		template = new Template(persistenceAPI + "/Index.tpl");

		source = new Source(package_webapp + "index");
		source.setExtension("jsp");
		source.setPackage(package_root);
		source.setModelname(modelname);
		source.setRepositoryname(repository);
		source.usingTemplate(template).withThisSourceFile().addContentIn(".+</ul>").savefile();
		
		return true;
	}

	private List<ModelField> loadFields(String fields) {
		
		List<ModelField> modelFields = new ArrayList<ModelField>();
		
		if (fields != null) {
			String[] fields_list = fields.split(",");
			for (String f: fields_list) {
				
				String field_name = f;
				String field_type = "String";
				
				int typeDivisor = field_name.indexOf(":");
				if ( typeDivisor > -1 ) {
					field_type = field_name.substring(typeDivisor+1);
					field_name = field_name.substring(0, typeDivisor);
				}
				
				ModelField newField = new ModelField(field_name, field_type);
				modelFields.add(newField);
			}
		}
		return modelFields;
	}	
	
}
