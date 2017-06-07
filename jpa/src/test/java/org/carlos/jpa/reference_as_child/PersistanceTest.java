package org.carlos.jpa.reference_as_child;

import static org.carlos.jpa.EntityManagerFactoryHelper.*;
import static org.junit.Assert.*;

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

public class PersistanceTest {

	private static EntityManagerFactory factory;

	@BeforeClass
	public static void beforeClass() {
		factory = EntityManagerFactoryProvider.get( "reference_as_child",
				Connections.hsqldb( true, true ) );
	}

	@AfterClass
	public static void afterClass() {
		factory.close();
	}

	@Before
	public void before() throws Exception {
		resetAutoIncrement( factory, "tobject", "id" );

		MockitoAnnotations.initMocks( this );
	}

	@After
	public void after() throws Exception {
		deleteEntities( factory, TObjectReference.class, TObject.class );
	}

	@Test
	public void deleteReference() throws Exception {

		TObjectReference reference = new TObjectReference();
		reference.setNamespace( "TEST" );
		reference.setValue( "VAL1" );

		TObject object = new TObject();
		object.setReference( reference );
		object.setString( "string" );

		createEntities( factory, object );

		{
			assertNotNull( getEntity( factory, TObjectReference.class,
					TObjectReferenceId.valueOf( "TEST", "VAL1" ) ) );
			List<TObject> entities = getEntities( factory, TObject.class );
			assertFalse( entities.isEmpty() );
			assertNotNull( entities.iterator().next().getReference() );
		}

		deleteEntities( factory, TObjectReference.class );

		{
			assertNull( getEntity( factory, TObjectReference.class,
					TObjectReferenceId.valueOf( "TEST", "VAL1" ) ) );
			List<TObject> entities = getEntities( factory, TObject.class );
			assertFalse( entities.isEmpty() );
			assertNull( entities.iterator().next().getReference() );
		}
	}

	@Test
	public void referenceExists() throws Exception {

		{
			TObjectReference reference = new TObjectReference();
			reference.setNamespace( "TEST" );
			reference.setValue( "VAL1" );

			TObject object = new TObject();
			object.setReference( reference );

			createEntities( factory, object );

			assertNotNull( getEntity( factory, TObjectReference.class,
					TObjectReferenceId.valueOf( "TEST", "VAL1" ) ) );
			List<TObject> entities = getEntities( factory, TObject.class );
			assertFalse( entities.isEmpty() );
			assertNotNull( entities.iterator().next().getReference() );
		}

		{
			TObjectReference reference = new TObjectReference();
			reference.setNamespace( "TEST" );
			reference.setValue( "VAL1" );

			TObject object = new TObject();
			object.setReference( reference );

			Exception exception = null;
			try {
				createEntities( factory, object );
				fail();
			} catch ( Exception e ) {
				exception = e;
			}
			assertNotNull( exception );
		}
	}

}
