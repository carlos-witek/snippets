package org.carlos.jpa.link_with_id_class;

import java.util.HashMap;
import java.util.Map;

public enum ReferenceType {

	ZER( 0 ),
	ONE( 1 ),
	TWO( 2 ),
	THR( 3 ),
	FOU( 4 ),
	FIV( 5 );

	private static final Map<Integer, ReferenceType> mapByValue = new HashMap<>();
	private final int value;

	static {
		for ( ReferenceType friendType : values() )
			mapByValue.put( friendType.getValue(), friendType );
	}

	ReferenceType( int value ) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ReferenceType valueOf( Integer value ) {
		if ( value == null )
			return null;
		return mapByValue.get( value );
	}
}
