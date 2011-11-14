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
	
}