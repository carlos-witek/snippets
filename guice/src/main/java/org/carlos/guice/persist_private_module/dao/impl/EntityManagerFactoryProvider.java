package org.carlos.guice.persist_private_module.dao.impl;

import javax.persistence.EntityManagerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;

public class EntityManagerFactoryProvider implements Provider<EntityManagerFactory> {

	private final PersistService persistService;
	private final Provider<EntityManagerFactory> emfProvider;

	@Inject
	public EntityManagerFactoryProvider( final PersistService persistService,
			final Provider<EntityManagerFactory> emfProvider ) {
		this.persistService = persistService;
		this.emfProvider = emfProvider;
	}

	@Override
	public EntityManagerFactory get() {
		EntityManagerFactory emf = emfProvider.get();
		if ( emf == null ) {
			persistService.start();
			emf = emfProvider.get();
		}
		return emf;
	}

}

