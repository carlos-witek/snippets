package org.carlos.snippets;

import com.google.common.base.MoreObjects;

public class ObjectWithValue {

	private int id;
	private int value;

	public int getId() {
		return id;
	}

	public ObjectWithValue( int id, int value ) {
		super();
		this.id = id;
		this.value = value;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue( int value ) {
		this.value = value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "id", id ).add( "value", value ).toString();
	}
}
