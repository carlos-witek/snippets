package org.carlos.jpa.link_with_id_class;

import static org.carlos.jpa.EntityManagerFactoryHelper.createEntities;
import static org.carlos.jpa.EntityManagerFactoryHelper.deleteEntities;
import static org.carlos.jpa.EntityManagerFactoryHelper.getEntities;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.carlos.jpa.Connections;
import org.carlos.jpa.EntityManagerFactoryProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class LinkTest {
	private static EntityManagerFactory factory;

	@BeforeClass
	public static void beforeClass() {
		factory = EntityManagerFactoryProvider.get( "link_with_id_class",
				Connections.hsqldb( false ) );
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
		deleteEntities( factory, Link.class, Reference.class );
	}

	@Test
	public void testLink() throws Exception {

		Reference reference1 = new Reference( 1, ReferenceType.valueOf( 2 ), "ref1" );
		Reference reference2 = new Reference( 3, ReferenceType.valueOf( 4 ), "ref2" );

		Link link11 = new Link( 1, reference1 );
		Link link12 = new Link( 1, reference2 );
		Link link21 = new Link( 2, reference1 );
		Link link22 = new Link( 2, reference2 );

		createEntities( factory, reference1, reference2, link11, link12, link21, link22 );

		{
			List<Link> resultList = getEntities( factory,
					"select l from Link l join l.reference ll where ll.value = 'ref1'",
					Link.class );

			assertEquals( 2, resultList.size() );
			assertEquals( 1, resultList.get( 0 ).getContextId() );
			assertEquals( 2, resultList.get( 1 ).getContextId() );
		}

		{
			List<Link> resultList = getEntities( factory,
					"select l from Link l join l.reference ll where ll.value = 'ref2'",
					Link.class );

			assertEquals( 2, resultList.size() );
			assertEquals( 1, resultList.get( 0 ).getContextId() );
			assertEquals( 2, resultList.get( 1 ).getContextId() );
		}

		{
			List<Link> resultList = getEntities( factory,
					"select l from Link l join l.reference ll where ll.value = 'ref1' and l.contextId = 1",
					Link.class );

			assertEquals( 1, resultList.size() );
			assertEquals( 1, resultList.get( 0 ).getContextId() );
		}

	}

}
