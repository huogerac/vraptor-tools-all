package filegen.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity
public class Issue  {

	@Id private ObjectId id;

	@Override
	public String toString() {
		//TODO: toString 
		return super.toString();
	}

	//TODO: getters and setters
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
}