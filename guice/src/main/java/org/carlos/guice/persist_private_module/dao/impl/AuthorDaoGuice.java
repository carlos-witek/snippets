package org.carlos.guice.persist_private_module.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;

import com.google.inject.persist.Transactional;

@Singleton
public class AuthorDaoGuice implements AuthorDao {

	private final Provider<EntityManager> ems;

	@Inject
	public AuthorDaoGuice( final Provider<EntityManager> ems ) {
		this.ems = ems;
	}

	@Override
	@Transactional
	public AuthorEntity getAuthor( final Long id ) {
		return ems.get().find( AuthorEntity.class, id );
	}

	@Override
	@Transactional
	public List<AuthorEntity> getAuthors() {
		return ems.get().createQuery( "from AuthorEntity", AuthorEntity.class ).getResultList();
	}

	@Override
	@Transactional
	public void createAuthor( final AuthorEntity author ) {
		ems.get().persist( author );
	}

}
