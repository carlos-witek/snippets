package org.carlos.snippets.guava.cache_with_nulls;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;

public class LoadingCacheWithNulls<K, V> implements LoadingCache<K, Optional<V>> {

	private static class OptionalCacheLoader<K, V> extends CacheLoader<K, Optional<V>> {
		private final CacheLoader<K, V> loader;

		public OptionalCacheLoader( CacheLoader<K, V> loader ) {
			super();
			this.loader = loader;
		}

		@Override
		public Optional<V> load( K key ) throws Exception {
			return Optional.fromNullable( loader.load( key ) );
		}

		public Map<K, Optional<V>> loadAll( Iterable<? extends K> keys ) throws Exception {
			Map<K, V> map = loader.loadAll( keys );

			Map<K, Optional<V>> optionalMap = new HashMap<>();
			for ( K key : keys )
				optionalMap.put( key, Optional.fromNullable( map.get( key ) ) );

			return optionalMap;
		};
	}

	private final LoadingCache<K, Optional<V>> delegate;

	public LoadingCacheWithNulls( CacheLoader<K, V> loader ) {
		this.delegate = CacheBuilder.newBuilder().build( new OptionalCacheLoader<>( loader ) );
	}

	public LoadingCacheWithNulls( CacheBuilderSpec spec, CacheLoader<K, V> loader ) {
		this.delegate = CacheBuilder.from( spec ).build( new OptionalCacheLoader<>( loader ) );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<V> getIfPresent( Object key ) {
		return invalidateAbsent( (K) key, delegate.getIfPresent( key ) );
	}

	@Override
	public Optional<V> get( K key, final Callable<? extends Optional<V>> valueLoader )
			throws ExecutionException {
		return invalidateAbsent( key, delegate.get( key, valueLoader ) );
	}

	@Override
	public ImmutableMap<K, Optional<V>> getAllPresent( Iterable<?> keys ) {
		return invalidateAbsent( delegate.getAllPresent( keys ) );
	}

	@Override
	public void put( K key, Optional<V> value ) {
		if ( value.isPresent() )
			delegate.put( key, value );
	}

	@Override
	public void putAll( Map<? extends K, ? extends Optional<V>> m ) {
		Map<K, Optional<V>> newm = new HashMap<>();
		for ( Entry<? extends K, ? extends Optional<V>> entry : m.entrySet() )
			if ( entry.getValue().isPresent() )
				newm.put( entry.getKey(), entry.getValue() );
		delegate.putAll( newm );
	}

	@Override
	public void invalidate( Object key ) {
		delegate.invalidate( key );
	}

	@Override
	public void invalidateAll( Iterable<?> keys ) {
		delegate.invalidateAll( keys );
	}

	@Override
	public void invalidateAll() {
		delegate.invalidateAll();
	}

	@Override
	public long size() {
		return delegate.size();
	}

	@Override
	public CacheStats stats() {
		return delegate.stats();
	}

	@Override
	public void cleanUp() {
		delegate.cleanUp();
	}

	@Override
	public Optional<V> get( K key ) throws ExecutionException {
		return invalidateAbsent( key, delegate.get( key ) );
	}

	@Override
	public Optional<V> getUnchecked( K key ) {
		return invalidateAbsent( key, delegate.getUnchecked( key ) );
	}

	@Override
	public ImmutableMap<K, Optional<V>> getAll( Iterable<? extends K> keys )
			throws ExecutionException {
		return invalidateAbsent( delegate.getAll( keys ) );
	}

	@Deprecated
	@Override
	public Optional<V> apply( K key ) {
		return delegate.apply( key );
	}

	@Override
	public void refresh( K key ) {
		delegate.refresh( key );
	}

	@Override
	public ConcurrentMap<K, Optional<V>> asMap() {
		return delegate.asMap();
	}

	private Optional<V> invalidateAbsent( K key, Optional<V> value ) {
		return invalidateAbsent( ImmutableMap.of( key, value ) ).get( key );
	}

	private ImmutableMap<K, Optional<V>> invalidateAbsent( ImmutableMap<K, Optional<V>> map ) {
		for ( Entry<K, Optional<V>> entry : map.entrySet() ) {
			if ( !entry.getValue().isPresent() )
				delegate.invalidate( entry.getKey() );
		}
		return map;
	}
}
