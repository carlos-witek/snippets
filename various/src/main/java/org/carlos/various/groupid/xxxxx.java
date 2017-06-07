package org.carlos.various.groupid;

public class xxxxx {
	public static void main( String[] args ) {
		System.out.println( 12345 + 54321 );

		System.out.println( Long.toHexString( 0xcafebabe ) );
		System.out.println( Long.toHexString( 0x100000000L + 0xcafebabe ) );

		char x = 'a';
		int i = 2;

		x += i;
		System.out.println( "[" + x + "]" );

		x = (char) ( x + i );
		System.out.println( "[" + x + "]" );

	}
}
