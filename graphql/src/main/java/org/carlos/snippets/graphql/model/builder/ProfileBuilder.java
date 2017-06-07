package org.carlos.snippets.graphql.model.builder;

import java.util.HashSet;
import java.util.Set;

import org.carlos.snippets.graphql.model.Account;
import org.carlos.snippets.graphql.model.Profile;

public final class ProfileBuilder {

	public static ProfileBuilder builder() {
		return new ProfileBuilder();
	}

	public static Profile copy( Profile other ) {
		Profile userProfile = new Profile();

		userProfile.setId( other.getId() );
		userProfile.setHolder( other.getHolder() );
		userProfile.setAccounts( other.getAccounts() );

		return userProfile;
	}

	private Profile template = new Profile();

	private ProfileBuilder() {
	}

	public ProfileBuilder id( Long id ) {
		template.setId( id );
		return this;
	}

	public ProfileBuilder holder( AccountHolderBuilder builder ) {
		template.setHolder( builder.build() );
		return this;
	}

	public ProfileBuilder accounts( Set<AccountBuilder> builders ) {
		Set<Account> accounts = new HashSet<Account>();
		for ( AccountBuilder builder : builders )
			accounts.add( builder.build() );
		template.setAccounts( accounts );
		return this;
	}

	public Profile build() {
		return copy( template );
	}

}
