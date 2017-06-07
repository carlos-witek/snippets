package org.carlos.jpa.multitable_entity;

import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "base")
@SecondaryTable(name = "extensiona")
public class ExtensionA extends Base {

	public String extension;

	public ExtensionA() {
		super();
	}

	public ExtensionA( long id, int type, String extension ) {
		super( id, type );
		setExtension( extension );
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension( String extendedString ) {
		this.extension = extendedString;
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
