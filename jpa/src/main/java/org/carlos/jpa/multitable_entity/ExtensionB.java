package org.carlos.jpa.multitable_entity;

import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "base")
@SecondaryTable(name = "extensionb")
public class ExtensionB extends Base {

	private Integer extension;

	public ExtensionB() {
		super();
	}

	public ExtensionB( long id, int type, Integer extension ) {
		super( id, type );
		setExtension( extension );
	}

	public Integer getExtension() {
		return extension;
	}

	public void setExtension( Integer extension ) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "id", getId() )
				.add( "type", getType() )
				.add( "extension", extension )
				.toString();
	}

}
