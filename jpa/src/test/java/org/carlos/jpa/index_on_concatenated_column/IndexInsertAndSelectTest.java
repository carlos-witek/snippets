package org.carlos.jpa.index_on_concatenated_column;

import static org.carlos.jpa.EntityManagerFactoryHelper.createEntities;
import static org.carlos.jpa.EntityManagerFactoryHelper.deleteEntities;
import static org.carlos.jpa.EntityManagerFactoryHelper.getEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManagerFactory;

import org.carlos.jpa.Connections;
import org.carlos.jpa.EntityManagerFactoryProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class IndexInsertAndSelectTest {
	private static EntityManagerFactory factory;
	private static List<Long> idsForSelect = new ArrayList<Long>();

	@BeforeClass
	public static void beforeClass() {
				factory = EntityManagerFactoryProvider.get( "index_on_concatenated_column",
						Connections.hsqldb( false ) );
//		factory = EntityManagerFactoryProvider.get( "index_on_concatenated_column",
//				Connections.mysql( "localhost", "test", "root", "" ) );

		Random random = new Random();

		for ( int i = 1; i < contextIdTotal * 2; i++ ) {
			idsForSelect.add( (long) random.nextInt( (int) contextIdTotal ) );
		}

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
		deleteEntities( factory, ColumnsConcatenated.class, ColumnsNotConcatenated.class );
	}

	private static final long totalRounds = 10;
	private static final long contextIdTotal = 200;
	private static final long namesIdTotal = 10;

	@Test
	public void testColumnsConcatenated_insert() throws Exception {
		long total = 0;

		for ( int i = 0; i < totalRounds; i++ ) {
			total += createColumnsConcatenated();
			deleteEntities( factory, ColumnsConcatenated.class );
		}

		System.out.println( "ColumnsConcatenated:" + ( total ) );

	}

	@Test
	public void testColumnsConcatenated_select() throws Exception {
		long total = 0;

		createColumnsConcatenated();

		for ( Long id : idsForSelect ) {
			long startTimeMillis = System.currentTimeMillis();
			getEntity( factory, ColumnsConcatenated.class, id );
			total += System.currentTimeMillis() - startTimeMillis;
		}

		System.out.println( "testColumnsConcatenated_select:" + total );
	}

	@Test
	public void testColumnsNotConcatenated() throws Exception {
		long total = 0;

		for ( int i = 0; i < totalRounds; i++ ) {
			total += createColumnsNotConcatenated();
			deleteEntities( factory, ColumnsNotConcatenated.class );
		}

		System.out.println( "ColumnsNotConcatenated:" + ( total ) );
	}

	@Test
	public void testColumnsNotConcatenated_select() throws Exception {

		long total = 0;

		createColumnsNotConcatenated();

		for ( Long id : idsForSelect ) {
			long startTimeMillis = System.currentTimeMillis();
			getEntity( factory, ColumnsNotConcatenated.class, id );
			total += System.currentTimeMillis() - startTimeMillis;
		}

		System.out.println( "testColumnsNotConcatenated_select:" + total );
	}

	/**
	 * insert {namesIdTotal} names for {contextIdTotal} contexts
	 */
	private long createColumnsConcatenated() {
		long total = 0;
		for ( int userId = 1; userId < contextIdTotal; userId++ ) {
			for ( int namesId = 1; namesId < namesIdTotal; namesId++ ) {
				ColumnsConcatenated columnsConcatenated = new ColumnsConcatenated( userId,
						"NS_NAME_" + namesId );

				long startTimeMillis = System.currentTimeMillis();
				createEntities( factory, columnsConcatenated );
				total += System.currentTimeMillis() - startTimeMillis;
			}
		}
		return total;
	}

	/**
	 * insert {namesIdTotal} names for {contextIdTotal} contexts
	 */
	private long createColumnsNotConcatenated() {
		long total = 0;

		for ( int userId = 1; userId < contextIdTotal; userId++ ) {
			for ( int namesId = 1; namesId < namesIdTotal; namesId++ ) {
				ColumnsNotConcatenated columnsNotConcatenated = new ColumnsNotConcatenated( userId,
						"NS",
						"NAME_" + namesId );
				long startTimeMillis = System.currentTimeMillis();
				createEntities( factory, columnsNotConcatenated );
				total += System.currentTimeMillis() - startTimeMillis;
			}
		}
		return total;
	}

}
