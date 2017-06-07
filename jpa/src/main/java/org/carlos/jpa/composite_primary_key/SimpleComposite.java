package org.carlos.jpa.composite_primary_key;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SimpleCompositePK.class)
public class SimpleComposite {

	private Long idLong;
	private String idString;
	private String content;

	public SimpleComposite() {
		super();
	}

	public SimpleComposite( Long idLobg, String idString, String content ) {
		super();
		setIdLong( idLobg );
		setIdString( idString );
		setContent( content );
	}

	@Id
	public Long getIdLong() {
		return idLong;
	}

	public void setIdLong( Long idLong ) {
		this.idLong = idLong;
	}

	@Id
	public String getIdString() {
		return idString;
	}

	public void setIdString( String idString ) {
		this.idString = idString;
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}
}
