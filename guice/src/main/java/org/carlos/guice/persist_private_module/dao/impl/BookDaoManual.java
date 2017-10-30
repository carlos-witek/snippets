package org.carlos.guice.persist_private_module.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.carlos.guice.persist_private_module.dao.BookDao;
import org.carlos.guice.persist_private_module.dao.entity.BookEntity;

@Singleton
public class BookDaoManual implements BookDao {

	private final EntityManagerFactory emf;

	@Inject
	public BookDaoManual( final EntityManagerFactory emf ) {
		this.emf = emf;
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
	public List<BookEntity> getBooks() {
		final EntityManager em = emf.createEntityManager();
		try {
			return em.createQuery( "from BookEntity", BookEntity.class ).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void createBook( BookEntity book ) {
		final EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist( book );
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

}
