package org.carlos.snippets.graphql.model;

public enum AccountArguments {
	ID( "id" ),
	TYPE( "type" );

	private final String identifier;

	AccountArguments( final String identifier ) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}
}
