package br.com.caelum.vraptor.filegen;

import java.io.InputStream;

public class Template {

	private String filename;
	private InputStream template;
	
	public Template(String filename) {
		this.filename  = filename;
		readTemplate();
	}

	private void readTemplate() {
		template = FileGenerator.class.getClassLoader().getResourceAsStream(filename);
	}
	
	public InputStream getTemplate() {
		return this.template;
	}
}
