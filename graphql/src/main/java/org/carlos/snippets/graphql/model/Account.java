package org.carlos.snippets.graphql.model;

import java.math.BigDecimal;

public class Account {

	private Long id;
	private AccountType type;
	private BigDecimal balance;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public AccountType getType() {
		return type;
	}

	public void setType( AccountType type ) {
		this.type = type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance( BigDecimal balance ) {
		this.balance = balance;
	}

}
