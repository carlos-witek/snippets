package org.carlos.jpa.reference_as_child;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.base.MoreObjects;

@SuppressWarnings("serial")
public class TObjectReferenceId implements Serializable {

	public static TObjectReferenceId valueOf( String namespace, String value ) {
		TObjectReferenceId id = new TObjectReferenceId();
		id.setNamespace( namespace );
		id.setValue( value );
		return id;
	}

	private String namespace;
	private String value;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace( String namespace ) {
		this.namespace = namespace;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof TObjectReferenceId ) {
			TObjectReferenceId id = (TObjectReferenceId) obj;
			return Objects.equals( namespace, id.namespace ) && Objects.equals( value, id.value );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash( namespace, value );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "namespace", namespace )
				.add( "value", value )
				.toString();
	}

}
