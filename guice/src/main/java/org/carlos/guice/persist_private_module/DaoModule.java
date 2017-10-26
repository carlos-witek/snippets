package org.carlos.guice.persist_private_module;

import com.google.inject.PrivateModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class DaoModule<T> extends PrivateModule {

	private final String key;
	private final Class<T> daoInterface;
	private final Class<? extends T> daoImplementation;

	public DaoModule( final String key, final Class<T> daoInterface,
			final Class<? extends T> daoImplementation ) {
		this.key = key;
		this.daoInterface = daoInterface;
		this.daoImplementation = daoImplementation;
	}

	@Override
	protected void configure() {
		install( new JpaPersistModule( key ) );

		bind( daoInterface ).to( daoImplementation );
		expose( daoInterface );
	}

}
