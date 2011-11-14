package filegen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.vraptor.filegen.Source;
import br.com.caelum.vraptor.filegen.Template;

public class SourceTest {

	@Test
	public void generateJavaModelSource() throws Exception {
		
		//dado um template
		Template template = new Template("Model.tpl");

		//Quando gerado um fonte 
		Source source = new Source("Issue");
		source.setPackage("test.filegen");
		source.usingTemplate(template).generateSource();
		
		//Entao 
		String expected = 
		"package test.filegen.model;\n" +
		"\n" +
		"import org.bson.types.ObjectId;\n" +
		"\n" +
		"import com.google.code.morphia.annotations.Entity;\n" +
		"import com.google.code.morphia.annotations.Id;\n" +
		"\n" +
		"@Entity\n" +
		"public class Issue  {\n" +
		"\n" +
		"	@Id private ObjectId id;\n" +
		"\n" +
		"	@Override\n" +
		"	public String toString() {\n" +
		"		//TODO: toString \n" +
		"		return super.toString();\n" +
		"	}\n" +
		"\n" +
		"	//TODO: getters and setters\n" +
		"	public ObjectId getId() {\n" +
		"		return id;\n" +
		"	}\n" +
		"	public void setId(ObjectId id) {\n" +
		"		this.id = id;\n" +
		"	}\n" +	
		"}";
		
		assertEquals(expected, source.getContent());
		
	}
	
	@Test
	public void generateJavaSource() throws Exception {
		
		//dado um template
		Template template = new Template("DAO.tpl");

		//Quando gerado um fonte 
		Source source = new Source("IssueDAO");
		source.setModelname("Issue");
		source.setPackage("test.filegen");
		source.usingTemplate(template).generateSource();
		
		//Entao 
		String expected = 
		"package test.filegen.dao;\n" +
		"\n" +
		"import java.net.UnknownHostException;\n" +
		"\n" +
		"import test.filegen.model.Issue;\n" +
		"\n" +
		"import com.google.code.morphia.Morphia;\n" +
		"import com.google.code.morphia.dao.BasicDAO;\n" +
		"import com.mongodb.Mongo;\n" +
		"import com.mongodb.MongoException;\n" +
		"\n" +
		"public class IssueDAO extends BasicDAO<Issue, String> {\n" +
		"\n" +
		"	private static IssueDAO INSTANCE = null;\n" +
		"\n" +
		"	public IssueDAO(Mongo mongo, Morphia morphia, String dbName) {\n" +
		"		super(mongo, morphia, dbName);\n" +
		"	}\n" +
		"\n" +
		"	public Issue findById(String id) {\n" +
		"		Issue result = super.getDatastore().find(Issue.class).field(\"id\").equal(id).get();\n" +
		"		return result;\n" +
		"	}\n" +
		"\n" +	
		"	//TODO: technical debt - refactoring to dependency injection\n" +
		"	public static IssueDAO getIssueDAO() {\n" +
		"\n" +
		"		if (INSTANCE == null) {\n" +
		"\n" +
		"			try {\n" +
		"\n" +				
		"				Mongo mongo = new Mongo(\"localhost\", 27017);\n" +
		"				Morphia morphia = new Morphia();\n" +
		"				morphia.map(Issue.class);\n" +
		"\n" +				
		"				INSTANCE = new IssueDAO(mongo, morphia, \"test\");\n" +
		"\n" +				
		"			} catch (UnknownHostException e) {\n" +
		"				throw new RuntimeException(e);\n" +
		"			} catch (MongoException e) {\n" +
		"				throw new RuntimeException(e);\n" +
		"			}\n" +
		"		}\n" +
		"		return INSTANCE;\n" +
		"	}\n" +
		"}";
		
		assertEquals(expected, source.getContent());
		
	}
	
	
	@Test
	public void generateJspSource() throws Exception {
		
		//dado um template
		Template template = new Template("JSP_list.tpl");

		//Quando gerado um fonte 
		Source source = new Source("list");
		source.setModelname("Issue");
		source.usingTemplate(template).generateSource();
		
		//Entao 
		String expected = 
		"<%@ include file=\"/header.jsp\" %>\n" + 
		"\n" +
		"<h1>Issue</h1>\n" +
		"<table>\n" +
		"	<c:forEach var=\"issue\" items=\"${issue_list}\">\n" +
		"		<tr>\n" +
		"			<td>${issue}</td>\n" +
		"		</tr>\n" +
		"	</c:forEach>\n" +
		"</table>\n" +
		"\n" +
		"<%@ include file=\"/footer.jsp\" %>";
		
		assertEquals(expected, source.getContent());
		
	}
	
	@Test
	public void saveFile() throws Exception {
		//dado um template
		Template template = new Template("Model.tpl");

		//Quando gerado um fonte 
		Source source = new Source("Issue");
		source.setPackage("filegen");
		source.setExtension("java");
		source.usingTemplate(template).generateSource();
		source.savenewfileTo("src/test/resources/filegen/model");
		
	}

	
	@Test
	public void saveFileWithSufix() throws Exception {
		//dado um template
		Template template = new Template("DAO.tpl");

		//Quando gerado um fonte 
		Source source = new Source("IssueDAO");
		source.setPackage("filegen");
		source.setExtension("java");
		source.setModelname("Issue");
		source.usingTemplate(template).generateSource();
		source.savenewfileTo("src/test/resources/filegen/dao");
		
	}
	
	
}