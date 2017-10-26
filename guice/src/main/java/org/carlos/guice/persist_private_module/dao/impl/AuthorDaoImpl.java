package org.carlos.guice.persist_private_module.dao.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;

public class AuthorDaoImpl implements AuthorDao {

	private final EntityManagerFactory emf;
	private final Provider<EntityManager> ems;

	@Inject
	public AuthorDaoImpl( final EntityManagerFactory emf, final Provider<EntityManager> ems ) {
		this.emf = emf;
		this.ems = ems;

		final EntityType<AuthorEntity> entityType = emf.getMetamodel().entity( AuthorEntity.class );
		StringBuilder countJPQLBuilder = new StringBuilder( "select count(*) from " )
				.append( entityType.getName() );

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
