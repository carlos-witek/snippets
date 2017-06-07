package org.carlos.snippets.guava.cache_with_nulls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class LoadingCacheWithNullsTest {

	@Test
	public void testNull() throws Exception {
		AtomicInteger loadCount = new AtomicInteger();
		CacheLoader<Long, String> loader = new CacheLoader<Long, String>() {
			public String load( Long key ) throws Exception {
				loadCount.incrementAndGet();
				return null;
			};
		};

		LoadingCache<Long, String> cache = CacheBuilder.newBuilder().build( loader );
		LoadingCacheWithNulls<Long, String> cacheWithNulls = new LoadingCacheWithNulls<Long, String>( loader );

		try {
			cache.get( 1L );
			fail( "expected InvalidCacheLoadException" );
		} catch ( InvalidCacheLoadException e ) {
			System.out.println( e );
			assertNull( "testException", e.getCause() );
		}

		try {
			cache.getUnchecked( 1L );
			fail( "expected InvalidCacheLoadException" );
		} catch ( InvalidCacheLoadException e ) {
			System.out.println( e );
			assertNull( "testException", e.getCause() );
		}

		{
			assertEquals( 2, loadCount.get() );
			assertNull( cacheWithNulls.get( 1L ).orNull() );
			assertEquals( 3, loadCount.get() );
		}

		{
			assertEquals( 3, loadCount.get() );
			assertNull( cacheWithNulls.get( 1L ).orNull() );
			assertEquals( 4, loadCount.get() );
		}

		{
			assertEquals( 4, loadCount.get() );
			assertNull( cacheWithNulls.getUnchecked( 1L ).orNull() );
			assertEquals( 5, loadCount.get() );
		}

		{
			assertEquals( 5, loadCount.get() );
			assertNull( cacheWithNulls.getUnchecked( 1L ).orNull() );
			assertEquals( 6, loadCount.get() );
		}
	}

	@Test
	public void testRuntimeException() throws Exception {
		CacheLoader<Long, String> loader = new CacheLoader<Long, String>() {
			public String load( Long key ) throws Exception {
				throw new RuntimeException( "testException" );
			};
		};

		LoadingCache<Long, String> cache = CacheBuilder.newBuilder().build( loader );
		LoadingCacheWithNulls<Long, String> cacheWithNulls = new LoadingCacheWithNulls<Long, String>( loader );

		try {
			cache.get( 1L );
			fail( "expected UncheckedExecutionException" );
		} catch ( UncheckedExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}

		try {
			cache.getUnchecked( 1L );
			fail( "expected UncheckedExecutionException" );
		} catch ( UncheckedExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}

		try {
			cacheWithNulls.get( 1L );
			fail( "expected UncheckedExecutionException" );
		} catch ( UncheckedExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}

		try {
			cacheWithNulls.getUnchecked( 1L );
			fail( "expected UncheckedExecutionException" );
		} catch ( UncheckedExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}
	}

	@Test
	public void testException() throws Exception {
		CacheLoader<Long, String> loader = new CacheLoader<Long, String>() {
			public String load( Long key ) throws Exception {
				throw new Exception( "testException" );
			}
		};

		LoadingCache<Long, String> cache = CacheBuilder.newBuilder().build( loader );
		LoadingCacheWithNulls<Long, String> cacheWithNulls = new LoadingCacheWithNulls<>( loader );

		try {
			cache.get( 1L );
			fail( "expected ExecutionException" );
		} catch ( ExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}

		try {
			cache.getUnchecked( 1L );
			fail( "expected ExecutionException" );
		} catch ( UncheckedExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}

		try {
			cacheWithNulls.get( 1L );
			fail( "expected ExecutionException" );
		} catch ( ExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}

		try {
			cacheWithNulls.getUnchecked( 1L );
			fail( "expected ExecutionException" );
		} catch ( UncheckedExecutionException e ) {
			System.out.println( e );
			assertEquals( "testException", e.getCause().getMessage() );
		}
	}

	@Test
	public void testGetAll_Null() throws Exception {
		AtomicInteger loadCount = new AtomicInteger();
		CacheLoader<Long, String> loader = new CacheLoader<Long, String>() {
			@Override
			public String load( Long key ) throws Exception {
				loadCount.incrementAndGet();
				return key.longValue() % 2 == 0 ? null : key.toString();
			}

			@Override
			public Map<Long, String> loadAll( Iterable<? extends Long> keys ) throws Exception {
				Map<Long, String> map = new HashMap<>();
				for ( Long key : keys )
					map.put( key, load( key ) );
				return map;
			}
		};

		LoadingCache<Long, String> cache = CacheBuilder.newBuilder().build( loader );
		LoadingCacheWithNulls<Long, String> cacheWithNulls = new LoadingCacheWithNulls<Long, String>( loader );

		List<Long> keys = Arrays.asList( 1L, 2L, 3L, 4L );

		try {
			cache.getAll( keys );
			fail( "expected InvalidCacheLoadException" );
		} catch ( InvalidCacheLoadException e ) {
			System.out.println( e );
			assertNull( "testException", e.getCause() );
		}

		{
			assertEquals( 4, loadCount.get() );
			assertEquals( 4, cacheWithNulls.getAll( keys ).size() );
			assertEquals( 8, loadCount.get() );
		}

		{
			assertEquals( 8, loadCount.get() );
			assertEquals( 4, cacheWithNulls.getAll( keys ).size() );
			assertEquals( 10, loadCount.get() );
		}

	}
}
