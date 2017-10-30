package org.carlos.guice.persist_private_module.dao;

import java.util.List;

import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;

public interface AuthorDao {

	AuthorEntity getAuthor( Long id );

	List<AuthorEntity> getAuthors();

	void createAuthor( AuthorEntity author );

}
