package br.com.caelum.vraptor.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import br.com.caelum.vraptor.filegen.FileGenerator;

/**
 * @goal create-controller
 */
public class ControllerMojo extends AbstractMojo {

	/**
	 * @required
	 * @parameter expression="${package_root}" 
	 */
	private String package_root;
	
	/**
	 * @required
	 * @parameter expression="${model}" 
	 */
	private String model;
	
	/**
	 * @required
	 * @parameter expression="${method}" 
	 */
	private String method;	
	

	public void execute() throws MojoExecutionException {

		
		display(">>>>>>>>>>>>> VRAPTOR - TOOLS");

		
		display("   generating controller to " + model);
		FileGenerator fg = new FileGenerator(package_root);
		try {
			
			fg.generateController(model, method);
			
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage());
		}
		
		display("   successfully");
		
		display("<<<<<<<<<<<<< VRAPTOR - TOOLS");

	}







	private void display(String message) {
		getLog().info(message);
	}
	
	
	//getters and setters
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPackage_root() {
		return package_root;
	}
	public void setPackage_root(String package_root) {
		this.package_root = package_root;
	}
}