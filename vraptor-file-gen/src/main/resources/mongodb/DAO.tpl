package %PACKAGE%.dao;

import org.bson.types.ObjectId;

import br.com.caelum.vraptor.ioc.Component;
import %PACKAGE%.common.MongoConfiguration;
import %PACKAGE%.model.%MODELNAME%;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;

@Component
public class %SOURCENAME% extends BasicDAO<%MODELNAME%, String> {

	public %SOURCENAME%(Mongo mongo, Morphia morphia,
			MongoConfiguration configuration) {
		super(mongo, morphia, configuration.getDbName());
	}

	public %MODELNAME% findById(String id) {
		ObjectId objectId = new ObjectId(id);
		%MODELNAME% result = super.getDatastore().find(%MODELNAME%.class).field("id").equal(objectId).get();
		return result;
	}

	public Query<%MODELNAME%> queryToFindMe(ObjectId objectId) {
		return super.getDatastore().createQuery(%MODELNAME%.class).field(Mapper.ID_KEY).equal(objectId);
	}

}