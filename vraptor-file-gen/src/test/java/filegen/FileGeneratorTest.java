package filegen;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.caelum.vraptor.filegen.FileGenerator;

public class FileGeneratorTest {

	FileGenerator fg = new FileGenerator("filegen")
							.usingSource("src/test/java/")
							.usingWebapp("src/test/java/");
	
	@Test
	public void generateModel() throws Exception {
		
		boolean result = fg.generateModel("Issue");
		assertTrue(result);
		
	}

}
