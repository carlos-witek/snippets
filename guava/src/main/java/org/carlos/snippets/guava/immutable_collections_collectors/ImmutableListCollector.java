package org.carlos.snippets.guava.immutable_collections_collectors;

import java.util.ArrayList;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ImmutableListCollector {

	public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toImmutableList() {
		return Collector.of( ImmutableList.Builder<T>::new, ImmutableList.Builder<T>::add,
				( left, right ) -> left.addAll( right.build() ), ImmutableList.Builder<T>::build );
	}

	public static void main( String[] args ) {
		final ArrayList<String> list = Lists.newArrayList( "1", "2", "3", "1", "2", "3" );

		System.out.println( list.stream().collect( toImmutableList() ) );
	}
}
