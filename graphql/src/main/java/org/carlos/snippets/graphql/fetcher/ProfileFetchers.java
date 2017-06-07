package org.carlos.snippets.graphql.fetcher;

import static org.carlos.snippets.graphql.model.ProfileArguments.ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.carlos.snippets.graphql.Repository;
import org.carlos.snippets.graphql.model.Profile;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class ProfileFetchers {

	public ProfileFetchers() {
		super();
	}

	public DataFetcher<Set<Profile>> getProfiles() {
		return new DataFetcher<Set<Profile>>() {

			@Override
			public Set<Profile> get( DataFetchingEnvironment environment ) {

				List<Predicate<Profile>> predicates = new ArrayList<Predicate<Profile>>();

				Long id = environment.getArgument( ID.getIdentifier() );
				if ( id != null ) {
					predicates.add( new Predicate<Profile>() {
						public boolean apply( Profile input ) {
							return id.equals( input.getId() );
						};
					} );
				}

				return Sets.filter( Repository.getProfiles(), Predicates.and( predicates ) );
			}

		};
	}

}
