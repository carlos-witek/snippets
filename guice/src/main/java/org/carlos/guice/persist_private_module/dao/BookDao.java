package org.carlos.guice.persist_private_module.dao;

import java.util.List;

import org.carlos.guice.persist_private_module.dao.entity.BookEntity;

public interface BookDao {

	BookEntity getBook( Long id );

	List<BookEntity> getBooks();

	void createBook( BookEntity book );

}
