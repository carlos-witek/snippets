package org.carlos.jpa.composite_primary_key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManagerFactory;

import org.carlos.jpa.Connections;
import org.carlos.jpa.EntityManagerFactoryHelper;
import org.carlos.jpa.EntityManagerFactoryProvider;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleCompositeTest {
	private static EntityManagerFactory factory;

	@BeforeClass
	public static void beforeClass() {
		factory = EntityManagerFactoryProvider.get( "composite_primary_key",
				Connections.hsqldb( false ) );
		//		factory = EntityManagerFactoryProvider.get( "composite_primary_key",
		//				Connections.mysql( "localhost", "test", "root", "" ) );

	}

	@Test
	public void testCreation() throws Exception {

		SimpleComposite a1 = new SimpleComposite( 1L, "A", "A1" );
		SimpleComposite a2 = new SimpleComposite( 2L, "A", "A2" );
		SimpleComposite b1 = new SimpleComposite( 1L, "B", "B1" );
		SimpleComposite b2 = new SimpleComposite( 2L, "B", "B2" );

		EntityManagerFactoryHelper.createEntities( factory, a1, a2, b1, b2 );

		{
			SimpleComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					SimpleComposite.class, new SimpleCompositePK( 1L, "A" ) );

			assertEquals( 1L, entity.getIdLong().longValue() );
			assertEquals( "A", entity.getIdString() );
			assertEquals( "A1", entity.getContent() );
		}

		{
			SimpleComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					SimpleComposite.class, new SimpleCompositePK( 1L, "B" ) );

			assertEquals( 1L, entity.getIdLong().longValue() );
			assertEquals( "B", entity.getIdString() );
			assertEquals( "B1", entity.getContent() );
		}

		{
			SimpleComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					SimpleComposite.class, new SimpleCompositePK( 1L, "C" ) );

			assertNull( entity );
		}

	}
}
