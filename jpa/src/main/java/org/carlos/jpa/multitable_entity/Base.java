package org.carlos.jpa.multitable_entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.MoreObjects;

@Entity
public class Base {

	private long id;
	private int type;

	public Base() {
		super();
	}

	public Base( long id, int type ) {
		super();
		this.id = id;
		this.type = type;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType( int type ) {
		this.type = type;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "id", id ).add( "type", type ).toString();
	}
}
