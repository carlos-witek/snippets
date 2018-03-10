package org.carlos.snippets.matcher_builder;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;
import static org.hamcrest.beans.PropertyUtil.propertyDescriptorsFor;
import static org.hamcrest.core.IsEqual.equalTo;

import static com.google.common.primitives.Primitives.allPrimitiveTypes;
import static com.google.common.primitives.Primitives.allWrapperTypes;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import com.google.common.base.Preconditions;

public class MatcherBuilder<T> {

	public static class MatcherBuilder2<T> extends MatcherBuilder<T> {

		private Object expected;
		private Set<String> excludeProperty = new HashSet<>();
		private Map<String, String> mapProperty = new HashMap<>();

		public MatcherBuilder2( Object expected ) {
			Preconditions.checkNotNull( expected );
			this.expected = expected;
		}

		public MatcherBuilder2<T> excludeProperty( String propertyName ) {
			Preconditions.checkNotNull( propertyName, "propertyName is null" );
			excludeProperty.add( propertyName );
			return this;
		}

		public MatcherBuilder2<T> excludeProperties( String propertyName,
				String... otherPropertyNames ) {
			Preconditions.checkNotNull( propertyName, "propertyName is null" );
			excludeProperty( propertyName );
			for ( String otherPropertyName : otherPropertyNames ) {
				excludeProperty( otherPropertyName );
			}
			return this;
		}

		public MatcherBuilder2<T> mapProperty( String fromPropertyName, String toPropertyName ) {
			Preconditions.checkNotNull( fromPropertyName, "fromPropertyName is null" );
			Preconditions.checkNotNull( toPropertyName, "toPropertyName is null" );
			mapProperty.put( fromPropertyName, toPropertyName );
			return this;
		}

		@Override
		public MatcherBuilder<T> hasProperty( String propertyName, Matcher<?> valueMatcher ) {
			return matcherBuilder().hasProperty( propertyName, valueMatcher );
		}

		@Override
		public MatcherBuilder<T> hasProperty( String propertyName, Object expected ) {
			return matcherBuilder().hasProperty( propertyName, expected );
		}

		@Override
		public Matcher<T> build() {
			return matcherBuilder().build();
		}

		private MatcherBuilder<T> matcherBuilder() {
			MatcherBuilder<T> matcherBuilder = new MatcherBuilder<T>();

			PropertyDescriptor[] descriptors = propertyDescriptorsFor( expected, Object.class );

			for ( PropertyDescriptor propertyDescriptor : descriptors ) {
				String propertyName = propertyDescriptor.getName();
				if ( excludeProperty.contains( propertyName ) )
					continue;
				Object propertyValue = readProperty( propertyDescriptor.getReadMethod(), expected );
				if ( mapProperty.containsKey( propertyName ) )
					matcherBuilder.hasProperty( mapProperty.get( propertyName ), propertyValue );
				else
					matcherBuilder.hasProperty( propertyName, propertyValue );
			}
			return matcherBuilder;

		}
	}

	public static <T> MatcherBuilder<T> builder() {
		return new MatcherBuilder<T>();
	}

	public static <T> MatcherBuilder2<T> samePropertyValuesAs( Object expected ) {
		return new MatcherBuilder2<T>( expected );
	}

	private static Object readProperty( Method method, Object target ) {
		try {
			return method.invoke( target, NO_ARGUMENTS );
		} catch ( Exception e ) {
			throw new IllegalArgumentException( "Could not invoke " + method + " on " + target, e );
		}
	}

	private Map<String, Matcher<? super T>> matchers = new LinkedHashMap<String, Matcher<? super T>>();

	public MatcherBuilder() {
		super();
	}

	public MatcherBuilder<T> hasProperty( String propertyName, Object expected ) {
		Preconditions.checkNotNull( propertyName, "propertyName is null" );
		if ( expected == null ) {
			return hasProperty( propertyName, nullValueMatcher() );
		} else if ( allPrimitiveTypes().contains( expected.getClass() ) || allWrapperTypes()
				.contains( expected.getClass() ) ) {
			return hasProperty( propertyName, equalTo( expected ) );
		} else if ( expected instanceof String ) {
			return hasProperty( propertyName, equalTo( expected ) );
		} else {
			return hasProperty( propertyName, samePropertyValuesAs( expected ).build() );
		}
	}

	@SuppressWarnings("unchecked")
	private Matcher<T> nullValueMatcher() {
		return (Matcher<T>) Matchers.nullValue();
	}

	public MatcherBuilder<T> hasProperty( String propertyName, Matcher<?> valueMatcher ) {
		Preconditions.checkNotNull( propertyName, "propertyName is null" );
		matchers.put( propertyName, Matchers.hasProperty( propertyName, valueMatcher ) );
		return this;
	}

	public Matcher<T> build() {
		return Matchers.allOf( matchers.values() );
	}
}
