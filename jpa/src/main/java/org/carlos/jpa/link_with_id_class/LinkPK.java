package org.carlos.jpa.link_with_id_class;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.base.MoreObjects;

@SuppressWarnings("serial")
public class LinkPK implements Serializable {

	private long contextId;
	private Reference reference;

	public LinkPK() {
		super();
	}

	public LinkPK( long contextId, Reference reference ) {
		super();
		setContextId( contextId );
		setReference( reference );
	}

	public long getContextId() {
		return contextId;
	}

	public void setContextId( long discriminator ) {
		this.contextId = discriminator;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference( Reference reference ) {
		this.reference = reference;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof LinkPK ) {
			LinkPK linkPK = (LinkPK) obj;
			return Objects.equals( contextId, linkPK.contextId ) && Objects.equals( reference,
					linkPK.reference );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash( contextId, reference );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "left", reference )
				.add( "discriminator", contextId )
				.toString();
	}
}
