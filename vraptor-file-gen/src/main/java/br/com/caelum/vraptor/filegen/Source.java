package br.com.caelum.vraptor.filegen;

import java.io.File;

public class Source {
	
	private final String source;
	
	private String extension;
	
	private String packagename;

	private String content;
	
	private String modelname;
	
	private Template template;
	
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

        this.content = codeformatter.generateSourceFromTemplate(template.getContent());
                
		return this;
		
	}	
	
	public Source updateSourceBefore(String text, String new_content) {
		this.content = codeformatter.updateSourceBefore(text, content, new_content);
		return this;
	}
	

	public String getContent() {
		return this.content;
	}

	public void savefile() throws Exception {
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

}
