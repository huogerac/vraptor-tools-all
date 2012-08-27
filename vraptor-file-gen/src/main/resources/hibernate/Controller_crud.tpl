package %PACKAGE%.controller;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

import %PACKAGE%.model.%MODELNAME%;
import %PACKAGE%.repository.%REPOSITORYNAME%;

@Resource
public class %REPOSITORYNAME%Controller {
	
	private final Result result;
	private final Validator validator;
	private final %REPOSITORYNAME% %REPOSITORYNAME_LOWERCASE%;

	public %REPOSITORYNAME%Controller(%REPOSITORYNAME% repository, Result result, Validator validator) {
		this.%REPOSITORYNAME_LOWERCASE% = repository;
		this.result = result;
		this.validator = validator;
	}	
	
	
	@Get @Path("/%REPOSITORYNAME_LOWERCASE%/novo")
	public void formulario() {
	}	
	
	@Get @Path("/%REPOSITORYNAME_LOWERCASE%/{id}")
	public %MODELNAME% edita(Long id) {
		return %REPOSITORYNAME_LOWERCASE%.load(id);
	}
	
	@Put @Path("/%REPOSITORYNAME_LOWERCASE%/{%MODELNAME_LOWERCASE%.id}")
	public void altera(%MODELNAME% %MODELNAME_LOWERCASE%) {
		%REPOSITORYNAME_LOWERCASE%.update(%MODELNAME_LOWERCASE%);
		result.redirectTo(%REPOSITORYNAME%Controller.class).lista();
	}	

	@Post @Path("/%REPOSITORYNAME_LOWERCASE%")
	public void adiciona(final %MODELNAME% %MODELNAME_LOWERCASE%) {
		
		validator.validate(%MODELNAME_LOWERCASE%);
		validator.onErrorUsePageOf(%REPOSITORYNAME%Controller.class).formulario();
		
		%REPOSITORYNAME_LOWERCASE%.save(%MODELNAME_LOWERCASE%);
		result.redirectTo(%REPOSITORYNAME%Controller.class).lista();
	}
	
	@Delete @Path("/%REPOSITORYNAME_LOWERCASE%/{id}")
	public void remove(Long id) {
		%MODELNAME% %MODELNAME_LOWERCASE% = %REPOSITORYNAME_LOWERCASE%.load(id);
		%REPOSITORYNAME_LOWERCASE%.delete(%MODELNAME_LOWERCASE%);
		result.redirectTo(%REPOSITORYNAME%Controller.class).lista();
	}	
	
	@Get @Path("/%REPOSITORYNAME_LOWERCASE%")
	public List<%MODELNAME%> lista() {
		return %REPOSITORYNAME_LOWERCASE%.listAll();
	}    
}