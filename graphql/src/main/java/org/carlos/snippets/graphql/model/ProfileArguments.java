package org.carlos.snippets.graphql.model;

public enum ProfileArguments {
	ID( "id" );

	private final String identifier;

	ProfileArguments( final String identifier ) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}
}
