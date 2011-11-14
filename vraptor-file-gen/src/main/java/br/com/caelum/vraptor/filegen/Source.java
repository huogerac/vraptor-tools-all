package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Source {
	
	private final String source;
	
	private String packagename;

	private String content;
	
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

		InputStream is = template.getTemplate();
		String content_temp = "";
		
        try {
        	
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.available() > 0) {
                baos.write(is.read());
            }
            
            content_temp = baos.toString();
            is.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        StringBuffer code = new StringBuffer(content_temp);
        
        replace(code, "%PACKAGE%", packagename);
        replace(code, "%MODELNAME%", source);
        replace(code, "%MODELNAME_LOWERCASE%", source.toLowerCase());
		
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
		ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
		File outputfile = this.file;
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
		
		
	}

	public Source savenewfile(String filename, String subfolder) throws Exception {
		subfolder += "/";
		FileSystem.createFolder(subfolder);

		this.file = FileSystem.writeNewFile(subfolder + filename);
		
		this.savefile();
		return this;
	}

	public void setPackage(String packagename) {
		this.packagename = packagename;
	}

}
