package org.carlos.guice.persist_private_module.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.MoreObjects;

@Entity
public class BookEntity {

	public Long id;
	public String title;
	public Long authorId;

	public BookEntity() {
		super();
	}

	public BookEntity( Long id, String title, Long authorId ) {
		super();
		setId( id );
		setTitle( title );
		setAuthorId( authorId );
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", id )
				.add( "title", title )
				.add( "authorId", authorId )
				.toString();
	}
}
