package org.carlos.evolution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Cell {

	private LinkedList<Specimen> specimens = new LinkedList<>();

	public Specimen specimen() {
		return specimens.getFirst();
	}

	public void addLast( Specimen specimen ) {
		specimens.addLast( specimen );
	}

	public List<Specimen> specimens() {
		return Collections.unmodifiableList( specimens );
	}

	public void specimens( List<Specimen> specimens ) {
		this.specimens = new LinkedList<>( specimens );
	}

}
