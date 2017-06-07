package org.carlos.codewars;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Circle {

	public static double area( double radius ) {
		if ( radius <= 0 )
			throw new IllegalArgumentException();
		BigDecimal radiusBigDecimal = BigDecimal.valueOf( radius );
		return BigDecimal.valueOf( Math.PI )
				.multiply( radiusBigDecimal )
				.multiply( radiusBigDecimal )
				.setScale( 2, RoundingMode.HALF_EVEN )
				.doubleValue();
	}

	public static void main( String[] args ) {
		System.out.println( area( 43.2673 ) );
	}

}
