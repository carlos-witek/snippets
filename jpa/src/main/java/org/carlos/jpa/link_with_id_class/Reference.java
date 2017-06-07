package org.carlos.jpa.link_with_id_class;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@IdClass(ReferencePK.class)
@Table(name = "friendship_idx",
		indexes = { @Index(name = "value_idx", columnList = "value", unique = false) })
public class Reference {

	private long contextId;
	private ReferenceType type;

	private String value;

	public Reference() {
		super();
	}

	public Reference( long id, ReferenceType type, String value ) {
		super();
		this.contextId = id;
		this.type = type;
		this.value = value;
	}

	@Id
	public long getContextId() {
		return contextId;
	}

	public void setContextId( long id ) {
		this.contextId = id;
	}

	@Id
	public ReferenceType getType() {
		return type;
	}

	public void setType( ReferenceType type ) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", contextId )
				.add( "enumId", type )
				.add( "value", value )
				.toString();
	}
}
