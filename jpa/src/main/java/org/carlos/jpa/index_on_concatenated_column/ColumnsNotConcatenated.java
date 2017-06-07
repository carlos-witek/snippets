package org.carlos.jpa.index_on_concatenated_column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "columns_not_concatenated",
		indexes = { @Index(name = "not_concatenated_idx",
				columnList = "contextId,namespace,name",
				unique = true) })
public class ColumnsNotConcatenated {

	private long id;
	private long contextId;
	private String namespace;
	private String name;

	public ColumnsNotConcatenated() {
		super();
	}

	public ColumnsNotConcatenated( long contextId, String namespace, String name ) {
		super();
		this.contextId = contextId;
		this.namespace = namespace;
		this.name = name;
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

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace( String namespace ) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", id )
				.add( "contextId", contextId )
				.add( "namespace", namespace )
				.add( "name", name )
				.toString();
	}

}
