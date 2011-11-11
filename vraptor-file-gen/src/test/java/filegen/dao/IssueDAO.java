package filegen.dao;

import java.net.UnknownHostException;

import filegen.model.IssueDAO;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class IssueDAODAO extends BasicDAO<IssueDAO, String> {
	
	private static IssueDAODAO INSTANCE = null;
	
	public IssueDAODAO(Mongo mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}
	
	public IssueDAO findById(String id) {
		IssueDAO result = super.getDatastore().find(IssueDAO.class).field("id").equal(id).get();
		return result;
	}
	
	//TODO: technical debt - refactoring to dependency injection
	public static IssueDAODAO getIssueDAODAO() {
		
		if (INSTANCE == null) {
			
			try {
				
				Mongo mongo = new Mongo("localhost", 27017);
				Morphia morphia = new Morphia();
				morphia.map(IssueDAO.class);
				
				INSTANCE = new IssueDAODAO(mongo, morphia, "test");
				
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			} catch (MongoException e) {
				throw new RuntimeException(e);
			}			
			
		}
		return INSTANCE;
	}	

}
