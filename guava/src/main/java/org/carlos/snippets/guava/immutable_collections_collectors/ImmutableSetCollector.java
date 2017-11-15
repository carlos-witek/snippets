package org.carlos.snippets.guava.immutable_collections_collectors;

import java.util.ArrayList;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class ImmutableSetCollector {

	public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet() {
		return Collector.of( ImmutableSet.Builder<T>::new, ImmutableSet.Builder<T>::add,
				( left, right ) -> left.addAll( right.build() ), ImmutableSet.Builder<T>::build );
	}

	public static void main( String[] args ) {
		final ArrayList<String> list = Lists.newArrayList( "1", "2", "3", "1", "2", "3" );

		System.out.println( list.stream().collect( toImmutableSet() ) );
	}
}
