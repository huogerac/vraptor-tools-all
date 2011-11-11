package %PACKAGE%.dao;

import java.net.UnknownHostException;

import %PACKAGE%.model.%MODELNAME%;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class %MODELNAME%DAO extends BasicDAO<%MODELNAME%, String> {
	
	private static %MODELNAME%DAO INSTANCE = null;
	
	public %MODELNAME%DAO(Mongo mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}
	
	public %MODELNAME% findById(String id) {
		%MODELNAME% result = super.getDatastore().find(%MODELNAME%.class).field("id").equal(id).get();
		return result;
	}
	
	//TODO: technical debt - refactoring to dependency injection
	public static %MODELNAME%DAO get%MODELNAME%DAO() {
		
		if (INSTANCE == null) {
			
			try {
				
				Mongo mongo = new Mongo("localhost", 27017);
				Morphia morphia = new Morphia();
				morphia.map(%MODELNAME%.class);
				
				INSTANCE = new %MODELNAME%DAO(mongo, morphia, "test");
				
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			} catch (MongoException e) {
				throw new RuntimeException(e);
			}			
			
		}
		return INSTANCE;
	}	

}
