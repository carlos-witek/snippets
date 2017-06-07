package org.carlos.evolution;

import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main( String[] args ) {

		Cell cell = null;

		List<Specimen> specimens = cell.specimens();
		Utils.score( specimens );
		cell.specimens( Arrays.asList( Utils.select( specimens ) ) );

	}
}
