package org.carlos.jpa.link_with_id_class;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@IdClass(LinkPK.class)
@Table(name = "friendship")
public class Link {

	private long contextId;
	private Reference reference;

	public Link() {
		super();
	}

	public Link( long contextId, Reference reference ) {
		super();
		setContextId( contextId );
		setReference( reference );
	}

	@Id
	public long getContextId() {
		return contextId;
	}

	public void setContextId( long contextId ) {
		this.contextId = contextId;
	}

	@Id
	@ManyToOne
	public Reference getReference() {
		return reference;
	}

	public void setReference( Reference right ) {
		this.reference = right;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "contextId", contextId )
				.add( "reference", reference )
				.toString();
	}

}
