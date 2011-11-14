package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Source {
	
	private final String source;
	
	private String extension;
	
	private String packagename;

	private String content;
	
	private String modelname;
	
	private Template template;
	
	private File file;
	
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

        StringBuffer code = new StringBuffer(template.getContent());
        
        replace(code, "%PACKAGE%", packagename);
        replace(code, "%SOURCENAME%", source);
        replace(code, "%SOURCENAME_LOWERCASE%", source.toLowerCase());
        if (modelname != null) {
        	replace(code, "%MODELNAME%", modelname);
        	replace(code, "%MODELNAME_LOWERCASE%", modelname.toLowerCase());
        }
		
        this.content = code.toString();
        
		return this;
		
	}	
	
	public Source updateSourceBefore(String text, String new_content) {
		int pos = content.lastIndexOf(text);
		this.content = content.substring(0, pos) + new_content + content.substring(pos, content.length());
		return this;
	}
	
    public static void replace(StringBuffer code, String marker, String newCode) {
        int posMarker = code.indexOf(marker);
        while (posMarker >= 0) {
        	code.replace(posMarker, posMarker + marker.length(), newCode);
        	posMarker = code.indexOf(marker);
        }
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
