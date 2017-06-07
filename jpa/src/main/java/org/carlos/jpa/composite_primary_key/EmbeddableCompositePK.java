package org.carlos.jpa.composite_primary_key;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import com.google.common.base.MoreObjects;

@SuppressWarnings("serial")
@Embeddable
public class EmbeddableCompositePK implements Serializable {
	private Long idLong;
	private String idString;

	public EmbeddableCompositePK() {
		super();
	}

	public EmbeddableCompositePK( Long idLong, String idString ) {
		super();
		setIdLong( idLong );
		setIdString( idString );
	}

	public Long getIdLong() {
		return idLong;
	}

	public void setIdLong( Long idLong ) {
		this.idLong = idLong;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString( String idString ) {
		this.idString = idString;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof EmbeddableCompositePK ) {
			EmbeddableCompositePK that = (EmbeddableCompositePK) obj;
			return Objects.equals( idLong, that.idLong ) && Objects.equals( idString, that.idString );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash( idLong, idString );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "idLong", idLong )
				.add( "idString", idString )
				.toString();
	}
}
