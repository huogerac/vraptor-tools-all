package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Template {

	private String filename;
	private InputStream template;
	private String content;
	
	public Template(String filename) {
		this.filename  = filename;
		readTemplate();
		convertToString();
	}

	private void readTemplate() {
		template = FileGenerator.class.getClassLoader().getResourceAsStream(filename);
	}
	
	private void convertToString() {
        try {
        	
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (template.available() > 0) {
                baos.write(template.read());
            }
            
            content = baos.toString();
            template.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public String getContent() {
		return this.content;
	}
}
