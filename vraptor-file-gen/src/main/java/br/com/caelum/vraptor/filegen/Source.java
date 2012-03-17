package br.com.caelum.vraptor.filegen;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Source {
	
	private final String source;
	
	private String extension;
	
	private String packagename;

	private String content;
	
	private String modelname;
	
	private List<ModelField> modelFields;
	
	private String repositoryname;
	
	private Template template;
	
	private Template fields_template;
	
	private File file;
	
	private CodeFormatter codeformatter = new CodeFormatter();
	
	public Source(String source) {
		this.source = source;
	}
	
	public Source(File file) {
		this.file = file;
		this.source = file.getName();
		this.content = FileSystem.readContent(file);
	}
	
	public Source usingTemplate(Template template) {
		this.template = template;
		return this;
	}
		
	public Source generateSource() {

		codeformatter.addConverter("%PACKAGE%", packagename);
		codeformatter.addConverter("%SOURCENAME%", source);
		codeformatter.addConverter("%SOURCENAME_LOWERCASE%", source.toLowerCase());
        if (modelname != null) {
        	codeformatter.addConverter("%MODELNAME%", modelname);
        	codeformatter.addConverter("%MODELNAME_LOWERCASE%", modelname.toLowerCase());
        }
        
        if (repositoryname != null) {
        	codeformatter.addConverter("%REPOSITORYNAME%", repositoryname);
        	codeformatter.addConverter("%REPOSITORYNAME_LOWERCASE%", repositoryname.toLowerCase());
        }
        
        if (modelFields != null) {
        	String fields_str = generateFields();
        	codeformatter.addConverter("%FIELDS%", fields_str);
        }
        
        this.content = codeformatter.generateSourceFromTemplate(template.getContent());
                
		return this;
		
	}	
	
	private String generateFields() {
		
		StringBuilder result = new StringBuilder(); 
		
		for (ModelField field: modelFields) {
			
			String type = field.getType();
			if (type == null) {
				type = "String";
			}

			CodeFormatter fcodeformatter = new CodeFormatter();
			
			for ( Map.Entry<String, String> entry : codeformatter.getConverters().entrySet() ) {
				fcodeformatter.addConverter(entry.getKey(), entry.getValue());
			}
			
			fcodeformatter.addConverter("%FIELDNAME%", field.getName());
			fcodeformatter.addConverter("%FIELDTYPE%", type);
			
			result.append( fcodeformatter.generateSourceFromTemplate(fields_template.getContent()) ).append("\n");
		}
		
		return result.toString();
	}

	public Source updateSourceBefore(String text, String new_content) {
		this.content = codeformatter.updateSourceBefore(text, content, new_content);
		return this;
	}
	

	public String getContent() {
		return this.content;
	}

	public void savefile() throws Exception  {
		FileSystem.writeToFile(this.file, content.getBytes());
	}

	public Source savenewfileTo(String subfolder) throws Exception {
		subfolder += "/";
		FileSystem.createFolder(subfolder);

		String filename = this.source + "." + this.extension;
		this.file = FileSystem.writeNewFile(subfolder + filename);
		
		this.savefile();
		return this;
	}

	public void setPackage(String packagename) {
		this.packagename = packagename;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public void setRepositoryname(String repository) {
		this.repositoryname = repository;
	}
	public String getRepositoryname() {
		return this.repositoryname;
	}

	public void setModelFields(List<ModelField> modelFields) {
		this.modelFields = modelFields;
	}
	
	public void addFieldsTemplate(Template template) {
		this.fields_template = template;
	}

	public Source withThisSourceFile() throws Exception {
		String filename = this.source + "." + this.extension;
		
		this.content = FileSystem.readContent(new File(filename));
		this.file = FileSystem.writeNewFile(filename);
		
		return this;
	}

	public Source addContentIn(String regex) {
		
		codeformatter.addConverter("%PACKAGE%", packagename);
		codeformatter.addConverter("%SOURCENAME%", source);
		codeformatter.addConverter("%SOURCENAME_LOWERCASE%", source.toLowerCase());
        if (modelname != null) {
        	codeformatter.addConverter("%MODELNAME%", modelname);
        	codeformatter.addConverter("%MODELNAME_LOWERCASE%", modelname.toLowerCase());
        }
        
        if (repositoryname != null) {
        	codeformatter.addConverter("%REPOSITORYNAME%", repositoryname);
        	codeformatter.addConverter("%REPOSITORYNAME_LOWERCASE%", repositoryname.toLowerCase());
        }
        
        if (modelFields != null) {
        	String fields_str = generateFields();
        	codeformatter.addConverter("%FIELDS%", fields_str);
        }
        
        String newContent = codeformatter.generateSourceFromTemplate(template.getContent());

        this.content = content.replaceAll(regex, newContent);
		
		return this;
	}
}
