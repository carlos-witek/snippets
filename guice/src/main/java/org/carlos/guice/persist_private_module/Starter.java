package org.carlos.guice.persist_private_module;

import java.util.List;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.BookDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;
import org.carlos.guice.persist_private_module.dao.entity.BookEntity;
import org.carlos.guice.persist_private_module.dao.impl.AuthorDaoImpl;
import org.carlos.guice.persist_private_module.dao.impl.BookDaoImpl;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Starter {
	public static void main( String[] args ) {

		List<Module> modules = ImmutableList.of(
				new DaoModule<>( "authors_and_books", AuthorDao.class, AuthorDaoImpl.class ),
				new DaoModule<>( "authors_and_books", BookDao.class, BookDaoImpl.class ) );

		Injector injector = Guice.createInjector( modules );

		AuthorDao authorDao = injector.getInstance( AuthorDao.class );
		authorDao.createAuthor( new AuthorEntity( 1L, "Adam" ) );
		AuthorEntity author = authorDao.getAuthor( 1L );

		System.out.println( author );

		BookDao bookDao = injector.getInstance( BookDao.class );
		bookDao.createBook( new BookEntity( 1L, "Title1", 1L ) );
		BookEntity book = bookDao.getBook( 1L );

		System.out.println( book );
	}

}
