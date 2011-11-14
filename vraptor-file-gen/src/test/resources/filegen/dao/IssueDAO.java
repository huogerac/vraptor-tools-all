package filegen.dao;

import java.net.UnknownHostException;

import filegen.model.Issue;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class IssueDAO extends BasicDAO<Issue, String> {
	
	private static IssueDAO INSTANCE = null;
	
	public IssueDAO(Mongo mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}
	
	public Issue findById(String id) {
		Issue result = super.getDatastore().find(Issue.class).field("id").equal(id).get();
		return result;
	}
	
	//TODO: technical debt - refactoring to dependency injection
	public static IssueDAO getIssueDAO() {
		
		if (INSTANCE == null) {
			
			try {
				
				Mongo mongo = new Mongo("localhost", 27017);
				Morphia morphia = new Morphia();
				morphia.map(Issue.class);
				
				INSTANCE = new IssueDAO(mongo, morphia, "test");
				
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			} catch (MongoException e) {
				throw new RuntimeException(e);
			}			
			
		}
		return INSTANCE;
	}	

}
