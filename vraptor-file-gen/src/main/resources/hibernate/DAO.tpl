package %PACKAGE%.repository.impl;

import org.hibernate.Session;

import br.com.bc.common.GenericDao;
import br.com.bc.model.Produto;
import br.com.bc.repository.Produtos;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class %SOURCENAME% extends GenericDao<%MODELNAME%, Long> implements %REPOSITORYNAME% {

	public %SOURCENAME%(Session session) {
		super(session);
	}

}