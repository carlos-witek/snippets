package org.carlos.snippets.guava.immutable_collections_collectors;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class ImmutableMapGroupByCollector {

	public static <T, K> Collector<T, Map<K, ImmutableList.Builder<T>>, ImmutableMap<K, ImmutableList<T>>> immutableGroupingBy(
			Function<? super T, ? extends K> classifier ) {

		return immutableGroupingBy( classifier, ImmutableListCollector.toImmutableList() );
	}

	private static <T, K, DS, D> Collector<T, Map<K, DS>, ImmutableMap<K, D>> immutableGroupingBy(
			Function<? super T, ? extends K> classifier, Collector<? super T, DS, D> downstream ) {

		return new Collector<T, Map<K, DS>, ImmutableMap<K, D>>() {

			@Override
			public Supplier<Map<K, DS>> supplier() {
				return HashMap::new;
			}

			@Override
			public BiConsumer<Map<K, DS>, T> accumulator() {
				return new BiConsumer<Map<K, DS>, T>() {
					@Override
					public void accept( Map<K, DS> m, T t ) {
						K key = Objects.requireNonNull( classifier.apply( t ),
								"element cannot be mapped to a null key" );
						downstream.accumulator().accept(
								m.computeIfAbsent( key, k -> downstream.supplier().get() ), t );

					}
				};
			}

			@Override
			public BinaryOperator<Map<K, DS>> combiner() {
				return new BinaryOperator<Map<K, DS>>() {
					@Override
					public Map<K, DS> apply( Map<K, DS> t, Map<K, DS> u ) {
						t.putAll( u );
						return t;
					}
				};
			}

			@Override
			public Function<Map<K, DS>, ImmutableMap<K, D>> finisher() {
				return new Function<Map<K, DS>, ImmutableMap<K, D>>() {
					@Override
					public ImmutableMap<K, D> apply( Map<K, DS> t ) {
						ImmutableMap.Builder<K, D> builder = ImmutableMap.builder();
						for ( Entry<K, DS> iterable_element : t.entrySet() ) {
							builder.put( iterable_element.getKey(),
									downstream.finisher().apply( iterable_element.getValue() ) );
						}
						return builder.build();
					}
				};
			}

			@Override
			public Set<Characteristics> characteristics() {
				return Collections.emptySet();
			}

		};
	}

	public static void main( String[] args ) {
		final ArrayList<String> list = Lists.newArrayList( "11", "12", "13", "21", "22", "23" );

		System.out.println( list.stream().collect( groupingBy( s -> s.charAt( 0 ) ) ) );
		final Map<Character, ImmutableList<String>> collect = list.stream().collect(
				groupingBy( s -> s.charAt( 0 ), ImmutableListCollector.toImmutableList() ) );
		final ImmutableMap<Character, ImmutableList<String>> copyOf = ImmutableMap
				.copyOf( collect );
		System.out.println( copyOf );

		final ImmutableMap<Character, ImmutableList<String>> collect2 = list.stream()
				.collect( immutableGroupingBy( s -> s.charAt( 0 ) ) );
		System.out.println( collect2 );
	}
}
