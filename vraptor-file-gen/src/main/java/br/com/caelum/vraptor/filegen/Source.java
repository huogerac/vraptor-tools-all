package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Source {
	
	private String filename;
	
	private String packagename;

	private String content;
	
	private Template template;
	
	public Source(String filename, String packagename) {
		this.filename = filename;
		this.packagename = packagename;
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
        replace(code, "%MODELNAME%", filename);
        replace(code, "%MODELNAME_LOWERCASE%", filename.toLowerCase());
		
        this.content = code.toString();
        
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

}
