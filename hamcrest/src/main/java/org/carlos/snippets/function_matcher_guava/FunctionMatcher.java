package org.carlos.snippets.function_matcher_guava;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.function.Function;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class FunctionMatcher {

	public static <T, R> Matcher<T> value( R expected, Function<T, R> function ) {
		return new FeatureMatcher<T, R>( equalTo( expected ), "value", "value" ) {
			@Override
			protected R featureValueOf( T actual ) {
				return function.apply( actual );
			}
		};
	}

}
