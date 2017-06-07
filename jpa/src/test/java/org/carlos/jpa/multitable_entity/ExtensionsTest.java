package org.carlos.jpa.multitable_entity;

import static org.carlos.jpa.EntityManagerFactoryHelper.createEntities;
import static org.carlos.jpa.EntityManagerFactoryHelper.deleteEntities;
import static org.carlos.jpa.EntityManagerFactoryHelper.getEntities;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.carlos.jpa.Connections;
import org.carlos.jpa.EntityManagerFactoryProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class ExtensionsTest {
	private static EntityManagerFactory factory;

	@BeforeClass
	public static void beforeClass() {
		factory = EntityManagerFactoryProvider.get( "multitable_entity", Connections.hsqldb( false ) );
	}

	@AfterClass
	public static void afterClass() {
		factory.close();
	}

	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks( this );
	}

	@After
	public void after() throws Exception {
		deleteEntities( factory, Base.class );
		deleteEntities( factory, ExtensionA.class );
		deleteEntities( factory, ExtensionB.class );
	}

	@Test
	public void testLink() throws Exception {

		ExtensionA extendedData1 = new ExtensionA( 1, 1, "1" );
		ExtensionB extendedData2 = new ExtensionB( 2, 2, 2 );

		createEntities( factory, extendedData1, extendedData2 );

		{
			List<Base> resultList = getEntities( factory, Base.class );

			assertEquals( 2, resultList.size() );
		}

		{
			List<ExtensionA> resultList = getEntities( factory, ExtensionA.class );

			assertEquals( 1, resultList.size() );
			assertEquals( 1, resultList.get( 0 ).getId() );
		}

		{
			List<ExtensionB> resultList = getEntities( factory, ExtensionB.class );

			assertEquals( 1, resultList.size() );
			assertEquals( 2, resultList.get( 0 ).getId() );

			deleteEntity( factory, ExtensionB.class, 2 );
		}

		System.out.println();
	}

	public static <T> void deleteEntity( EntityManagerFactory entityManagerFactory,
			Class<T> entityClass, long id ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.createQuery(
					String.format( "delete from %s o where o.id = :id", entityClass.getSimpleName() ) )
					.setParameter( "id", id )
					.executeUpdate();
			transaction.commit();
		} catch ( RuntimeException e ) {
			if ( transaction.isActive() )
				transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

}
