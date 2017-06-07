package org.carlos.jpa.composite_primary_key;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmbeddableComposite {

	private EmbeddableCompositePK pk;
	private String content;

	public EmbeddableComposite() {
		super();
	}

	public EmbeddableComposite( EmbeddableCompositePK pk, String content ) {
		super();
		setPk( pk );
		setContent( content );
	}

	@Id
	public EmbeddableCompositePK getPk() {
		return pk;
	}

	public void setPk( EmbeddableCompositePK pk ) {
		this.pk = pk;
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}
}
