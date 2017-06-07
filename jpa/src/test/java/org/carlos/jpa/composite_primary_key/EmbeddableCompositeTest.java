package org.carlos.jpa.composite_primary_key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManagerFactory;

import org.carlos.jpa.Connections;
import org.carlos.jpa.EntityManagerFactoryHelper;
import org.carlos.jpa.EntityManagerFactoryProvider;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmbeddableCompositeTest {
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

		EmbeddableCompositePK a1pk = new EmbeddableCompositePK( 1L, "A" );
		EmbeddableComposite a1 = new EmbeddableComposite( a1pk, "A1" );
		EmbeddableCompositePK a2pk = new EmbeddableCompositePK( 2L, "A" );
		EmbeddableComposite a2 = new EmbeddableComposite( a2pk, "A2" );
		EmbeddableCompositePK b1pk = new EmbeddableCompositePK( 1L, "B" );
		EmbeddableComposite b1 = new EmbeddableComposite( b1pk, "B1" );
		EmbeddableCompositePK b2pk = new EmbeddableCompositePK( 2L, "B" );
		EmbeddableComposite b2 = new EmbeddableComposite( b2pk, "B2" );

		EntityManagerFactoryHelper.createEntities( factory, a1, a2, b1, b2 );

		{
			EmbeddableComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					EmbeddableComposite.class, a1pk );

			assertEquals( 1L, entity.getPk().getIdLong().longValue() );
			assertEquals( "A", entity.getPk().getIdString() );
			assertEquals( "A1", entity.getContent() );
		}

		{
			EmbeddableComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					EmbeddableComposite.class, b1pk );

			assertEquals( 1L, entity.getPk().getIdLong().longValue() );
			assertEquals( "B", entity.getPk().getIdString() );
			assertEquals( "B1", entity.getContent() );
		}

		{
			EmbeddableComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					EmbeddableComposite.class, new EmbeddableCompositePK( 1L, "C" ) );

			assertNull( entity );
		}

	}
}
