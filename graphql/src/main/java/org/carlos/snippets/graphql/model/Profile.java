package org.carlos.snippets.graphql.model;

import java.util.Set;

public class Profile {

	private Long id;
	private AccountHolder holder;
	private Set<Account> accounts;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public AccountHolder getHolder() {
		return holder;
	}

	public void setHolder( AccountHolder holder ) {
		this.holder = holder;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts( Set<Account> accounts ) {
		this.accounts = accounts;
	}

}
