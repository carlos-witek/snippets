package org.carlos.guice.persist_private_module;

import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class DaoModule<T> extends PrivateModule {

	private final String persistenceUnit;
	private final Class<T> daoInterface;
	private final Class<? extends T> daoImplementation;

	public DaoModule( final String persistenceUnit, final Class<T> daoInterface,
			final Class<? extends T> daoImplementation ) {
		this.persistenceUnit = persistenceUnit;
		this.daoInterface = daoInterface;
		this.daoImplementation = daoImplementation;
	}

	@Override
	protected void configure() {
		install( new JpaPersistModule( persistenceUnit ) );

		bind( daoInterface ).to( daoImplementation );
		expose( daoInterface );

		bind( PersistService.class ).annotatedWith( Names.named( persistenceUnit ) )
				.toProvider( getProvider( PersistService.class ) );
		expose( PersistService.class ).annotatedWith( Names.named( persistenceUnit ) );
	}

}
