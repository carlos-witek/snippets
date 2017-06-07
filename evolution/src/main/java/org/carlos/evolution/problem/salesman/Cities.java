package org.carlos.evolution.problem.salesman;

import java.util.List;
import java.util.Map;

public class Cities {
	private Map<Long, Double> mapx;
	private Map<Long, Double> mapy;

	public Cities() {
		super();
	}

	public void set( long id, double x, double y ) {
		mapx.put( id, x );
		mapy.put( id, y );
	}

	public double distance( List<Long> ids ) {
		double distance = 0.0;
		for ( int i = 0; i < ids.size(); i++ ) {
			int j = i < ids.size() - 1 ? i + 1 : 0;
			distance += distance( i, j );
		}
		return distance;
	}

	public double distance( long id1, long id2 ) {
		double dx = mapx.get( id1 ) - mapx.get( id2 );
		double dy = mapy.get( id1 ) - mapy.get( id2 );
		return Math.sqrt( dx * dx + dy * dy );
	}

}
