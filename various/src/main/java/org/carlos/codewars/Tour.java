package org.carlos.codewars;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tour {
	public static int tour( String[] friends, String[][] friendsTowns, Map<String, Double> distances ) {
		Map<String, String> friendTownMap = Stream.of( friendsTowns ).collect(
				Collectors.toMap( friendTown -> friendTown[0], friendTown -> friendTown[1] ) );

		Double reduce = Stream.of( friends )
				.map( city -> distances.get( friendTownMap.get( city ) ) )
				.filter( Objects::nonNull )
				.reduce( 0.0, ( a, b ) -> {
					System.out.println( a + " " + b );
					return Math.sqrt( b * b - a * a );
				} );
		System.out.println( reduce );

		distances.put( "X0", 0.0 );

		double total = 0.0;

		String city = "X0";
		for ( String friend : friends ) {
			String friendTown = friendTownMap.get( friend );
			if ( friendTown == null )
				continue;

			total += Math.sqrt( distances.get( friendTown ) * distances.get( friendTown ) - distances.get( city ) * distances.get( city ) );
			city = friendTown;
		}
		total += distances.get( city );

		// your code
		return (int) total;
	}

	public static void main( String[] args ) {
		String[] friends1 = new String[] { "A1", "A2", "A3", "A4", "A5" };
		String[][] fTowns1 = {	new String[] { "A1", "X1" },
								new String[] { "A2", "X2" },
								new String[] { "A3", "X3" },
								new String[] { "A4", "X4" } };
		Map<String, Double> distTable1 = new HashMap<String, Double>();
		distTable1.put( "X1", 100.0 );
		distTable1.put( "X2", 200.0 );
		distTable1.put( "X3", 250.0 );
		distTable1.put( "X4", 300.0 );

		System.out.println( Tour.tour( friends1, fTowns1, distTable1 ) );
	}
}
