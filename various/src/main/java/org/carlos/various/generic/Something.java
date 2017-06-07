package org.carlos.various.generic;

import java.util.Optional;

public class Something<T> {
	public void doSomething( Optional<T> optional ) {
		System.out.println( optional.getClass() );
		System.out.println( optional.get().getClass() );
		call( optional.get() );
	}

	public void call( T t ) {
	}

}
