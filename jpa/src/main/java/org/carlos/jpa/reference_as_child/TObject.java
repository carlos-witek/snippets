package org.carlos.jpa.reference_as_child;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.google.common.base.MoreObjects;

@Entity
public class TObject {
	private long id;
	private TObjectReference reference;
	private String string;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	@OneToOne(cascade = { CascadeType.PERSIST },
			fetch = FetchType.EAGER,
			optional = true,
			mappedBy = "object")
	public TObjectReference getReference() {
		return reference;
	}

	public void setReference( TObjectReference reference ) {
		if ( this.reference != null )
			this.reference.setObject( null );
		this.reference = reference;
		if ( this.reference != null )
			this.reference.setObject( this );
	}

	public String getString() {
		return string;
	}

	public void setString( String string ) {
		this.string = string;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", id )
				.add( "reference", reference )
				.toString();
	}
}
