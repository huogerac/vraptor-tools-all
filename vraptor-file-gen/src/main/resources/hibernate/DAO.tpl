package %PACKAGE%.repository.impl;

import org.hibernate.Session;

import %PACKAGE%.common.GenericDao;
import %PACKAGE%.model.%MODELNAME%;
import %PACKAGE%.repository.%REPOSITORYNAME%;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class %SOURCENAME% extends GenericDao<%MODELNAME%, Long> implements %REPOSITORYNAME% {

	public %SOURCENAME%(Session session) {
		super(session);
	}

}