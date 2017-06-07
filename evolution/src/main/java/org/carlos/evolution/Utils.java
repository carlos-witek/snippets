package org.carlos.evolution;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Utils {
	private final static Random RANDOM = new Random();

	public static void score( List<Specimen> specimens ) {
		// TODO Auto-generated method stub

	}

	public static final Specimen select( List<Specimen> specimens ) {
		TreeMap<Long, Specimen> map = new TreeMap<>();

		long totalScore = 0;
		for ( Specimen specimen : specimens )
			map.put( totalScore += specimen.getScore(), specimen );

		return map.ceilingEntry( RANDOM.nextLong() % totalScore ).getValue();
	}

}
