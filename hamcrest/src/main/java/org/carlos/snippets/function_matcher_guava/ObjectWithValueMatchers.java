package org.carlos.snippets.function_matcher_guava;

import static org.hamcrest.core.IsEqual.equalTo;

import org.carlos.snippets.ObjectWithValue;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class ObjectWithValueMatchers {

	public static Matcher<ObjectWithValue> id( final int expectedId ) {
		return new FeatureMatcher<ObjectWithValue, Integer>( equalTo( expectedId ), "id", "id" ) {
			@Override
			protected Integer featureValueOf( ObjectWithValue actual ) {
				return Integer.valueOf( actual.getId() );
			}
		};
	}

	public static Matcher<ObjectWithValue> value( final int expectedId ) {
		return new FeatureMatcher<ObjectWithValue, Integer>( equalTo( expectedId ),
				"value",
				"value" ) {
			@Override
			protected Integer featureValueOf( ObjectWithValue actual ) {
				return Integer.valueOf( actual.getValue() );
			}
		};
	}

}
