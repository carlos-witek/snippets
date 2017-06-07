package org.carlos.codewars;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Consecutives {
	public static List<Integer> sumConsecutives( List<Integer> s ) {
		LinkedList<Integer> results = new LinkedList<>();
		results.addLast( s.get( 0 ).intValue() );
		for ( int i = 1; i < s.size(); i++ )
			results.addLast( s.get( i - 1 ).intValue() == s.get( i ).intValue() ? results.removeLast()
					.intValue() + s.get( i ).intValue() : s.get( i ).intValue() );
		return results;
	}

	public static void main( String[] args ) {
		System.out.println( sumConsecutives( Arrays.asList( 1, 1, 7, 7, 3, 7 ) ) );
		System.out.println( sumConsecutives( Arrays.asList( -5, -5, 7, 7, 12, 0 ) ) );

	}
}
