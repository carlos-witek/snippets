package org.carlos.guice.persist_private_module.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.MoreObjects;

@Entity
public class AuthorEntity {

	public Long id;
	public String name;

	public AuthorEntity() {
	}

	public AuthorEntity( final Long id, final String name ) {
		setId( id );
		setName( name );
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "id", id ).add( "name", name ).toString();
	}
}
