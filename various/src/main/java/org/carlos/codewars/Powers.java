package org.carlos.codewars;

import java.math.BigInteger;

public class Powers {
	public static BigInteger powers( int length ) {
		return BigInteger.valueOf( 2 ).pow( length );
	}

	public static void main( String[] args ) {
		System.out.println( powers( 63 ) );
		System.out.println( powers( 100 ) );
		System.out.println( powers( 200 ) );
		System.out.println( powers( 300 ) );
		System.out.println( powers( 400 ) );
	}
}
