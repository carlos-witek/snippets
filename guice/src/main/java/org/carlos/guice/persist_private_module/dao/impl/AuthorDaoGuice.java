package org.carlos.guice.persist_private_module.dao.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;

@Singleton
public class AuthorDaoImpl2 implements AuthorDao {

	private final Provider<EntityManagerFactory> emfs;
	private final Provider<EntityManager> ems;

	@Inject
	public AuthorDaoImpl2( final EntityManagerFactoryProvider emfs, final Provider<EntityManager> ems ) {
		this.emfs = emfs;
		this.ems = ems;
	}
	

	@Override
	public AuthorEntity getAuthor( Long id ) {
		final EntityManager em = emf.createEntityManager();
		try {
			return em.find( AuthorEntity.class, id );
		} finally {
			em.close();
		}
	}

	@Override
	@Transactional
	public void createAuthor( AuthorEntity author ) {
		ems.get().persist( author );
	}

}
