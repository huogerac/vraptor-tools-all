package %PACKAGE%.dao;

import java.net.UnknownHostException;

import %PACKAGE%.model.%MODELNAME%;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class %SOURCENAME% extends BasicDAO<%MODELNAME%, String> {

	private static %SOURCENAME% INSTANCE = null;

	public %SOURCENAME%(Mongo mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}

	public %MODELNAME% findById(String id) {
		%MODELNAME% result = super.getDatastore().find(%MODELNAME%.class).field("id").equal(id).get();
		return result;
	}

	//TODO: technical debt - refactoring to dependency injection
	public static %SOURCENAME% get%SOURCENAME%() {

		if (INSTANCE == null) {

			try {

				Mongo mongo = new Mongo("localhost", 27017);
				Morphia morphia = new Morphia();
				morphia.map(%MODELNAME%.class);

				INSTANCE = new %SOURCENAME%(mongo, morphia, "test");

			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			} catch (MongoException e) {
				throw new RuntimeException(e);
			}
		}
		return INSTANCE;
	}
}