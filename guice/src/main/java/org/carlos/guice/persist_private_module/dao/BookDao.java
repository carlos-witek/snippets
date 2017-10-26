package org.carlos.guice.persist_private_module.dao;

import org.carlos.guice.persist_private_module.dao.entity.BookEntity;

public interface BookDao {

	BookEntity getBook( Long id );

	void createBook( BookEntity book );

}
