package br.com.caelum.vraptor.filegen;

public class ModelField {
	
	private final String name;
	
	private final String type;

	public ModelField(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
}
