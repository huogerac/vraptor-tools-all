package filegen;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.caelum.vraptor.filegen.FileGenerator;

public class FileGeneratorTest {


	
	@Test
	public void generateModel() throws Exception {
		
		String model = "Issue";
		String repository = "Issues";
		String fields = "status,description,type:int,test";
		String packagename = "filegen";
		
		FileGenerator fg = new FileGenerator(model, fields, repository, packagename)
			.usingSource("src/test/resources/")
			.usingResources("src/test/resources/")
			.toPersistenceAPI(FileGenerator.HIBERNATE);		
		
		boolean result = fg.generateModel();
		assertTrue(result);
		
	}
	
	//@Test
	public void generateController() throws Exception  {
		
		String model = "Issue";
		String repository = "Issues";
		String fields = "status,description,type:int,test";
		String packagename = "filegen";
		
		FileGenerator fg = new FileGenerator(model, repository, fields, packagename)
			.usingSource("src/test/resources/")
			.usingWebapp("src/test/resources/")
			.usingResources("")
			.toPersistenceAPI(FileGenerator.HIBERNATE);			
		
		boolean result = fg.generateController("Issue", "Issues", "status,description,type:int,test", "crud");
		assertTrue(result);
		
	}

}
