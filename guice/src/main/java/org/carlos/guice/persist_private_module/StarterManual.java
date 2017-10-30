package org.carlos.guice.persist_private_module;

import java.util.List;

import org.carlos.guice.persist_private_module.dao.AuthorDao;
import org.carlos.guice.persist_private_module.dao.BookDao;
import org.carlos.guice.persist_private_module.dao.entity.AuthorEntity;
import org.carlos.guice.persist_private_module.dao.entity.BookEntity;
import org.carlos.guice.persist_private_module.dao.impl.AuthorDaoManual;
import org.carlos.guice.persist_private_module.dao.impl.BookDaoManual;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistService;

public class StarterManual {
	public static void main( String[] args ) {

		List<Module> modules = ImmutableList.of(
				new DaoModule<>( "authors", AuthorDao.class, AuthorDaoManual.class ),
				new DaoModule<>( "books", BookDao.class, BookDaoManual.class ) );

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
