package org.carlos.various.generic;

import java.util.Optional;

public class dsfsdfsdf {

	public static void main( String[] args ) {
		Something<String> something = new Something<String>() {
			@Override
			public void call( String t ) {
				System.out.println( t.length() );
			}
		};

		something.doSomething( Optional.of( "abc" ) );

		something.doSomething( (Optional) Optional.of( 123L ) );

	}
}
