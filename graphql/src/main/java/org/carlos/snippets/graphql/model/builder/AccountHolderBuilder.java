package org.carlos.snippets.graphql.model.builder;

import org.carlos.snippets.graphql.model.AccountHolder;

public final class AccountHolderBuilder {

	public static AccountHolderBuilder builder() {
		return new AccountHolderBuilder();
	}

	public static AccountHolder copy( AccountHolder other ) {
		AccountHolder user = new AccountHolder();

		user.setId( other.getId() );
		user.setFirstName( other.getFirstName() );
		user.setLastName( other.getLastName() );

		return user;
	}

	private AccountHolder template = new AccountHolder();

	private AccountHolderBuilder() {
		super();
	}

	public AccountHolderBuilder id( Long id ) {
		template.setId( id );
		return this;
	}

	public AccountHolderBuilder firstName( String firstName ) {
		template.setFirstName( firstName );
		return this;
	}

	public AccountHolderBuilder lastName( String lastName ) {
		template.setLastName( lastName );
		return this;
	}

	public AccountHolder build() {
		return copy( template );
	}

}
