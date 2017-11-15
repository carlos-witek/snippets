package org.carlos.snippets.guava.immutable_collections_collectors;

import static java.util.function.Function.identity;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class ImmutableMapCollector {

	public static <T, K, U> Collector<T, ?, ImmutableMap<K, U>> toImmutableMap(
			Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends U> valueMapper ) {

		return Collector.of( ImmutableMap.Builder<K, U>::new,
				( map, element ) -> map.put( keyMapper.apply( element ),
						valueMapper.apply( element ) ),
				( left, right ) -> left.putAll( right.build() ),
				ImmutableMap.Builder<K, U>::build );
	}

	public static void main( String[] args ) {
		final ArrayList<String> list = Lists.newArrayList( "11", "12", "13", "21", "22", "23" );

		System.out.println( list.stream().collect( toImmutableMap( identity(), identity() ) ) );
	}
}
