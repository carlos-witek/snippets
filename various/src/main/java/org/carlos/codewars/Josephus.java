package org.carlos.codewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Josephus {
	public static <T> List<T> josephusPermutation( final List<T> items, final int k ) {
		List<T> results = new ArrayList<T>( items.size() );
		LinkedList<T> list = new LinkedList<T>( items );
		while ( !list.isEmpty() ) {
			for ( int i = 1; i < k; i++ )
				list.addLast( list.removeFirst() );
			results.add( list.removeFirst() );
		}
		return results;
	}

	public static void main( String[] args ) {
		System.out.println( josephusPermutation( Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ), 1 ) );
		System.out.println( josephusPermutation( Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ), 2 ) );
	}
}
