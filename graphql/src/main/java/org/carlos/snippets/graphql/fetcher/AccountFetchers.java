package org.carlos.snippets.graphql.fetcher;

import static org.carlos.snippets.graphql.model.AccountArguments.ID;
import static org.carlos.snippets.graphql.model.AccountArguments.TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.carlos.snippets.graphql.Repository;
import org.carlos.snippets.graphql.model.Account;
import org.carlos.snippets.graphql.model.AccountType;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class AccountFetchers {

	public AccountFetchers() {
	}

	public DataFetcher<Set<Account>> getAccounts() {
		return new DataFetcher<Set<Account>>() {

			@Override
			public Set<Account> get( DataFetchingEnvironment environment ) {

				List<Predicate<Account>> predicates = new ArrayList<Predicate<Account>>();

				Long id = environment.getArgument( ID.getIdentifier() );
				if ( id != null ) {
					predicates.add( new Predicate<Account>() {
						public boolean apply( Account input ) {
							return id.equals( input.getId() );
						};
					} );
				}

				AccountType type = environment.getArgument( TYPE.getIdentifier() );
				if ( type != null ) {
					predicates.add( new Predicate<Account>() {
						public boolean apply( Account input ) {
							return type.equals( input.getType() );
						};
					} );
				}

				return Sets.filter( Repository.getAccounts(), Predicates.and( predicates ) );
			}

		};
	}

}
