package org.carlos.snippets.graphql.fetcher;

import static org.carlos.snippets.graphql.model.AccountArguments.ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.carlos.snippets.graphql.Repository;
import org.carlos.snippets.graphql.model.AccountHolder;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class AccountHolderFetchers {

	public AccountHolderFetchers() {
	}

	public DataFetcher<Set<AccountHolder>> getAccountHolders() {
		return new DataFetcher<Set<AccountHolder>>() {

			@Override
			public Set<AccountHolder> get( DataFetchingEnvironment environment ) {

				List<Predicate<AccountHolder>> predicates = new ArrayList<Predicate<AccountHolder>>();

				Long id = environment.getArgument( ID.getIdentifier() );
				if ( id != null ) {
					predicates.add( new Predicate<AccountHolder>() {
						public boolean apply( AccountHolder input ) {
							return id.equals( input.getId() );
						};
					} );
				}

				return Sets.filter( Repository.getAccountHolders(), Predicates.and( predicates ) );
			}

		};
	}

}
