package org.carlos.jpa.composite_primary_key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManagerFactory;

import org.carlos.jpa.Connections;
import org.carlos.jpa.EntityManagerFactoryHelper;
import org.carlos.jpa.EntityManagerFactoryProvider;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmbeddableIdCompositeTest {
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

		EmbeddableIdCompositePK a1pk = new EmbeddableIdCompositePK( 1L, "A" );
		EmbeddableIdComposite a1 = new EmbeddableIdComposite( a1pk, "A1" );
		EmbeddableIdCompositePK a2pk = new EmbeddableIdCompositePK( 2L, "A" );
		EmbeddableIdComposite a2 = new EmbeddableIdComposite( a2pk, "A2" );
		EmbeddableIdCompositePK b1pk = new EmbeddableIdCompositePK( 1L, "B" );
		EmbeddableIdComposite b1 = new EmbeddableIdComposite( b1pk, "B1" );
		EmbeddableIdCompositePK b2pk = new EmbeddableIdCompositePK( 2L, "B" );
		EmbeddableIdComposite b2 = new EmbeddableIdComposite( b2pk, "B2" );

		EntityManagerFactoryHelper.createEntities( factory, a1, a2, b1, b2 );

		{
			EmbeddableIdComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					EmbeddableIdComposite.class, a1pk );

			assertEquals( 1L, entity.getPk().getIdLong().longValue() );
			assertEquals( "A", entity.getPk().getIdString() );
			assertEquals( "A1", entity.getContent() );
		}

		{
			EmbeddableIdComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					EmbeddableIdComposite.class, b1pk );

			assertEquals( 1L, entity.getPk().getIdLong().longValue() );
			assertEquals( "B", entity.getPk().getIdString() );
			assertEquals( "B1", entity.getContent() );
		}

		{
			EmbeddableIdComposite entity = EntityManagerFactoryHelper.getEntity( factory,
					EmbeddableIdComposite.class, new EmbeddableIdCompositePK( 1L, "C" ) );

			assertNull( entity );
		}

	}
}
