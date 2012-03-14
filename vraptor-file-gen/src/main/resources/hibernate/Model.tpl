package %PACKAGE%.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class %SOURCENAME% implements Serializable {

    //TODO: You should generate a serialVersionUID

	@Id @GeneratedValue
	private Long id;
	
	%FIELDS%


	//TODO: getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	//TODO: You should generate all getters and setters
		
	@Override
	public String toString() {
		//TODO: toString 
		return super.toString();
	}
}