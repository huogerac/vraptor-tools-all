package %PACKAGE%.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="%REPOSITORYNAME_LOWERCASE%")
public class %SOURCENAME% implements Serializable {

    //TODO: You should generate a serialVersionUID

	@Id @GeneratedValue
	private Long id;

	// You also can use:
	// @Column(name ="other_name")
	// @Column(unique=true, nullable=false)
	// @Column(length=255)
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