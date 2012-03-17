package filegen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.vraptor.filegen.Source;
import br.com.caelum.vraptor.filegen.Template;

public class TemplateTest {

	@Test
	public void generateJavaModelSource() throws Exception {
		
		//dado um template
		//Template template = new Template("mongodb/Model.tpl");
		Template template = new Template("hibernate.cfg.xml");
		String str = template.getContent();
		
		String regex = ".+</session-factory>";
		str = str.replaceAll(regex, "\t\tboooo!\n\n\t</session-factory>");
		
		System.out.println(str);
		
		//assertEquals(expected, source.getContent());
		
	}
	

	
	
}