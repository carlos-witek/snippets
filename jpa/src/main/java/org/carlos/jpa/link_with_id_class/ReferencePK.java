package org.carlos.jpa.link_with_id_class;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.base.MoreObjects;

@SuppressWarnings("serial")
public class ReferencePK implements Serializable {

	private long contextId;
	private ReferenceType type;

	public long getContextId() {
		return contextId;
	}

	public void setContextId( long id ) {
		this.contextId = id;
	}

	public ReferenceType getType() {
		return type;
	}

	public void setType( ReferenceType type ) {
		this.type = type;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof ReferencePK ) {
			ReferencePK referencePK = (ReferencePK) obj;
			return Objects.equals( contextId, referencePK.contextId ) && Objects.equals( type,
					referencePK.type );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash( contextId, type );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", contextId )
				.add( "type", type )
				.toString();
	}

}
