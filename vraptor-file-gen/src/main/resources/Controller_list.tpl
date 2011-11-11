package %PACKAGE%.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import %PACKAGE%.dao.%MODELNAME%DAO;
import %PACKAGE%.model.%MODELNAME%;

@Resource
public class %MODELNAME%Controller {
	
	private final Result result;
	
	private %MODELNAME%DAO dao = %MODELNAME%DAO.get%MODELNAME%DAO();
	
	public %MODELNAME%Controller(Result result) {
		this.result = result;
	}
	
	@Get @Path("/%MODELNAME_LOWERCASE%/list")
    public void list() {
    	List<%MODELNAME%> list = dao.getDatastore().find(%MODELNAME%.class).asList();
    	
    	result.include("%MODELNAME_LOWERCASE%_list", list);
    }

}
