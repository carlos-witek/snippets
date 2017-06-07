package org.carlos.jpa.reference_as_child;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.google.common.base.MoreObjects;

@Entity
@IdClass(TObjectReferenceId.class)
public class TObjectReference {
	private String namespace;
	private String value;

	private TObject object;

	@Id
	@Column(name = "namespace", length = 4, nullable = false)
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace( String namespace ) {
		this.namespace = namespace;
	}

	@Id
	@Column(name = "value", length = 40, nullable = false)
	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	@OneToOne(cascade = { CascadeType.PERSIST }, optional = false)
	@JoinColumn(name = "object_id",
			nullable = false,
			updatable = false,
			foreignKey = @ForeignKey(name = "object_fk"))
	public TObject getObject() {
		return object;
	}

	public void setObject( TObject object ) {
		this.object = object;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "namespace", namespace )
				.add( "value", value )
				.toString();
	}
}
