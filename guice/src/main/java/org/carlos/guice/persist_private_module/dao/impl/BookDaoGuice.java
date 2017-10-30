package org.carlos.guice.persist_private_module.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.carlos.guice.persist_private_module.dao.BookDao;
import org.carlos.guice.persist_private_module.dao.entity.BookEntity;

@Singleton
public class BookDaoGuice implements BookDao {

	private final Provider<EntityManager> ems;

	@Inject
	public BookDaoGuice( final Provider<EntityManager> ems ) {
		this.ems = ems;
	}

	@Override
	@Transactional
	public BookEntity getBook( Long id ) {
		return ems.get().find( BookEntity.class, id );
	}

	@Override
	@Transactional
	public List<BookEntity> getBooks() {
		return ems.get().createQuery( "from BookEntity e", BookEntity.class ).getResultList();
	}

	@Override
	@Transactional
	public void createBook( BookEntity book ) {
		ems.get().persist( book );
	}

}
