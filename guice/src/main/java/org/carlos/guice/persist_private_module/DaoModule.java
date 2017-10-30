package org.carlos.guice.persist_private_module;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class DaoModule extends PrivateModule {
	private static class DaoModuleClasses<T> {
		public final Class<T> daoInterface;
		public final Class<? extends T> daoImplementation;

		public DaoModuleClasses( final Class<T> daoInterface,
				final Class<? extends T> daoImplementation ) {
			this.daoInterface = daoInterface;
			this.daoImplementation = daoImplementation;
		}
	}

	private final String persistenceUnit;
	private final List<DaoModuleClasses<?>> daos;

	public DaoModule( final String persistenceUnit ) {
		this.persistenceUnit = persistenceUnit;
		this.daos = new LinkedList<>();
	}

	public <T> DaoModule register( final Class<T> daoInterface,
			final Class<? extends T> daoImplementation ) {
		daos.add( new DaoModuleClasses<>( daoInterface, daoImplementation ) );
		return this;
	}

	@Override
	protected void configure() {
		install( new JpaPersistModule( persistenceUnit ) );

		// bind and expose daos
		for ( DaoModuleClasses<?> classes : daos ) {
			@SuppressWarnings("unchecked")
			final DaoModuleClasses<Object> classesTypeErased = (DaoModuleClasses<Object>) classes;

			bind( classesTypeErased.daoInterface ).to( classesTypeErased.daoImplementation );
			expose( classesTypeErased.daoInterface );
		}

		// expose PersistService by a new annotation
		bind( PersistService.class ).annotatedWith( Names.named( persistenceUnit ) )
				.toProvider( getProvider( PersistService.class ) );
		expose( PersistService.class ).annotatedWith( Names.named( persistenceUnit ) );
	}

}
