package org.carlos.guice.persist_private_module;

import java.util.List;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.BookDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;
import org.carlos.guice.persist_private_module.dao.entity.BookEntity;
import org.carlos.guice.persist_private_module.dao.impl.AuthorDaoGuice;
import org.carlos.guice.persist_private_module.dao.impl.BookDaoGuice;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistService;

public class StarterGuice {
	public static void main( String[] args ) throws ClassNotFoundException {

		List<Module> modules = ImmutableList.of(
				new DaoModule( "authors" ).register( AuthorDao.class, AuthorDaoGuice.class ),
				new DaoModule( "books" ).register( BookDao.class, BookDaoGuice.class ) );

		Injector injector = Guice.createInjector( modules );
		try {
			injector.getInstance( keyPersistService( "authors" ) ).start();
			injector.getInstance( keyPersistService( "books" ) ).start();

			// application code
			AuthorDao authorDao = injector.getInstance( AuthorDao.class );
			authorDao.createAuthor( new AuthorEntity( 1L, "Adam" ) );
			System.out.println( authorDao.getAuthor( 1L ) );
			System.out.println( authorDao.getAuthors() );

			BookDao bookDao = injector.getInstance( BookDao.class );
			bookDao.createBook( new BookEntity( 1L, "Title1", 1L ) );
			System.out.println( bookDao.getBook( 1L ) );
			System.out.println( bookDao.getBooks() );

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			injector.getInstance( keyPersistService( "authors" ) ).stop();
			injector.getInstance( keyPersistService( "books" ) ).stop();
		}

	}

	private static Key<PersistService> keyPersistService( final String persistenceUnit ) {
		return Key.get( PersistService.class, Names.named( persistenceUnit ) );
	}

}
