package org.carlos.guice.persist_private_module.dao;

import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;

public interface AuthorDao {

	AuthorEntity getAuthor( Long id );

	void createAuthor( AuthorEntity author );

}
