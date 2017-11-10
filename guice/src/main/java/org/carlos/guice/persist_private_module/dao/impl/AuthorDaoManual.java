package org.carlos.guice.persist_private_module.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;

@Singleton
public class AuthorDaoManual implements AuthorDao {

	private final EntityManagerFactory emf;

	@Inject
	public AuthorDaoManual( final EntityManagerFactory emf ) {
		this.emf = emf;
	}

	@Override
	public AuthorEntity getAuthor( final Long id ) {
		final EntityManager em = emf.createEntityManager();
		try {
			return em.find( AuthorEntity.class, id );
		} finally {
			em.close();
		}
	}

	@Override
	public List<AuthorEntity> getAuthors() {
		final EntityManager em = emf.createEntityManager();
		try {
			return em.createQuery( "from AuthorEntity", AuthorEntity.class ).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void createAuthor( final AuthorEntity author ) {
		final EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist( author );
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

}
