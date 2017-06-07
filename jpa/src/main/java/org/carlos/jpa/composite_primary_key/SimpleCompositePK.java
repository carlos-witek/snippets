package org.carlos.jpa.composite_primary_key;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.base.MoreObjects;

@SuppressWarnings("serial")
public class SimpleCompositePK implements Serializable {
	private Long idLong;
	private String idString;

	public SimpleCompositePK() {
		super();
	}

	public SimpleCompositePK( Long idLong, String idString ) {
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
		if ( obj instanceof SimpleCompositePK ) {
			SimpleCompositePK that = (SimpleCompositePK) obj;
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
