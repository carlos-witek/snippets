package org.carlos.jpa.composite_primary_key;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class EmbeddableIdComposite {

	private EmbeddableIdCompositePK pk;
	private String content;

	public EmbeddableIdComposite() {
		super();
	}

	public EmbeddableIdComposite( EmbeddableIdCompositePK pk, String content ) {
		super();
		setPk( pk );
		setContent( content );
	}

	@EmbeddedId
	public EmbeddableIdCompositePK getPk() {
		return pk;
	}

	public void setPk( EmbeddableIdCompositePK pk ) {
		this.pk = pk;
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}
}
