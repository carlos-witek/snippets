package org.carlos.snippets.graphql.model.builder;

import java.math.BigDecimal;

import org.carlos.snippets.graphql.model.Account;
import org.carlos.snippets.graphql.model.AccountType;

public class AccountBuilder {

	public static AccountBuilder builder() {
		return new AccountBuilder();
	}

	public static Account copyOf( Account other ) {
		Account account = new Account();

		account.setId( other.getId() );
		account.setType( other.getType() );
		account.setBalance( other.getBalance() );

		return account;
	}

	private Account template = new Account();

	private AccountBuilder() {
		super();
	}

	public AccountBuilder id( Long id ) {
		template.setId( id );
		return this;
	}

	public AccountBuilder type( AccountType type ) {
		template.setType( type );
		return this;
	}

	public AccountBuilder balance( BigDecimal balance ) {
		template.setBalance( balance );
		return this;
	}

	public Account build() {
		return copyOf( template );
	}
}
