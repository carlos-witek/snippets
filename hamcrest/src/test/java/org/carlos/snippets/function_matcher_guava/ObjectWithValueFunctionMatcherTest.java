package org.carlos.snippets.function_matcher_guava;

import static org.carlos.snippets.function_matcher_guava.FunctionMatcher.value;
import static org.junit.Assert.assertThat;

import org.carlos.snippets.ObjectWithValue;
import org.junit.Test;

public class ObjectWithValueFunctionMatcherTest {
	@Test
	public void regural_matcher() throws Exception {
		ObjectWithValue simple = new ObjectWithValue( 10, 9 );

		assertThat( simple, ObjectWithValueMatchers.id( 10 ) );
		assertThat( simple, ObjectWithValueMatchers.value( 9 ) );
	}

	@Test
	public void function_matcher() throws Exception {
		ObjectWithValue simple = new ObjectWithValue( 10, 9 );

		assertThat( simple, value( 10, ( o ) -> o.getId() ) );
		assertThat( simple, value( 9, ( o ) -> o.getValue() ) );
	}
}
