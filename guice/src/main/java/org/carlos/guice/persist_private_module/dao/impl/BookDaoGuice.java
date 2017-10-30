package org.carlos.guice.persist_private_module.dao.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.carlos.guice.persist_private_module.dao.BookDao;
import org.carlos.guice.persist_private_module.dao.entity.BookEntity;

public class BookDaoImpl2 implements BookDao {

	private final EntityManagerFactory emf;
	private final Provider<EntityManager> ems;

	@Inject
	public BookDaoImpl2( final EntityManagerFactory emf, final Provider<EntityManager> ems ) {
		this.emf = emf;
		this.ems = ems;
	}

	@Override
	public BookEntity getBook( Long id ) {
		final EntityManager em = emf.createEntityManager();
		try {
			return em.find( BookEntity.class, id );
		} finally {
			em.close();
		}
	}

	@Override
	@Transactional
	public void createBook( BookEntity book ) {
		ems.get().persist( book );
	}

}
