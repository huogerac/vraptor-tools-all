package filegen;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.caelum.vraptor.filegen.FileGenerator;

public class FileGeneratorTest {

	FileGenerator fg = new FileGenerator("filegen")
							.usingSource("src/test/resources/")
							.usingWebapp("src/test/resources/")
							.toPersistenceAPI(FileGenerator.HIBERNATE);
	
	@Test
	public void generateModel() throws Exception {
		
		boolean result = fg.generateModel("Issue", "AllIssues", "status,description,type:int,test");
		assertTrue(result);
		
	}
	
	@Test
	public void generateController() throws Exception  {
		
		boolean result = fg.generateController("Issue", "list");
		assertTrue(result);
		
	}

}
