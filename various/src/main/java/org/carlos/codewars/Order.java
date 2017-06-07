package org.carlos.codewars;

import java.util.Iterator;
import java.util.TreeMap;

public class Order {
	public static String order( String words ) {
		if ( words == null )
			return null;
		if ( words.trim().isEmpty() )
			return "";

		TreeMap<Integer, String> map = new TreeMap<>();

		for ( String string : words.split( "\\s+" ) )
			map.put( Integer.valueOf( string.replaceAll( "\\D", "" ) ), string );

		StringBuilder builder = new StringBuilder();
		Iterator<Integer> iterator = map.keySet().iterator();
		if ( iterator.hasNext() )
			builder.append( map.get( iterator.next() ) );
		while ( iterator.hasNext() ) {
			builder.append( " " ).append( map.get( iterator.next() ) );
		}
		return builder.toString();
	}

	public static void main( String[] args ) {
		System.out.println( order( "" ) );
	}
}
