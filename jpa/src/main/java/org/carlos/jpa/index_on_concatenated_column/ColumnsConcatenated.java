package org.carlos.jpa.index_on_concatenated_column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "columns_concatenated",
		indexes = { @Index(name = "concatenated_idx",
				columnList = "contextId,namespaceName",
				unique = true) })
public class ColumnsConcatenated {

	private long id;
	private long contextId;
	private String namespaceName;

	public ColumnsConcatenated() {
		super();
	}

	public ColumnsConcatenated( long contextId, String namespaceName ) {
		super();
		this.contextId = contextId;
		this.namespaceName = namespaceName;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public long getContextId() {
		return contextId;
	}

	public void setContextId( long contextId ) {
		this.contextId = contextId;
	}

	public String getNamespaceName() {
		return namespaceName;
	}

	public void setNamespaceName( String namespaceName ) {
		this.namespaceName = namespaceName;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", id )
				.add( "contextId", contextId )
				.add( "namespaceName", namespaceName )
				.toString();
	}

}
