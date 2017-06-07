package org.carlos.snippets.graphql.schema;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

import static org.carlos.snippets.graphql.schema.types.GraphQLBigDecimal.GraphQLBigDecimal;

import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import org.carlos.snippets.graphql.fetcher.AccountFetchers;
import org.carlos.snippets.graphql.fetcher.AccountHolderFetchers;
import org.carlos.snippets.graphql.fetcher.ProfileFetchers;

public class UserGraphQLSchema {

	final AccountHolderFetchers userFetchers;
	final ProfileFetchers userProfileFetchers;
	final AccountFetchers accountFetchers;

	public UserGraphQLSchema( final AccountHolderFetchers userFetchers,
			final ProfileFetchers userProfileFetchers, final AccountFetchers accountFetchers ) {
		this.userFetchers = userFetchers;
		this.userProfileFetchers = userProfileFetchers;
		this.accountFetchers = accountFetchers;
	}

	public GraphQLSchema getSchema() {
		return GraphQLSchema.newSchema().query( createQuery() ).build();
	}

	private GraphQLObjectType createQuery() {
		return newObject().name( "Query" )
				.field( newFieldDefinition().name( "profile" )
						.type( new GraphQLList( profile() ) )
						.argument(
								newArgument().name( "id" )
										.description( "id of user" )
										.type( GraphQLInt )
										.build() )
						.dataFetcher( userFetchers.getAccountHolders() )
						.build() )
				.field( newFieldDefinition().name( "accountHolder" )
						.type( new GraphQLList( accountHolder() ) )
						.argument(
								newArgument().name( "id" )
										.description( "id of user" )
										.type( GraphQLInt )
										.build() )
						.dataFetcher( userProfileFetchers.getProfiles() )
						.build() )
				.field( newFieldDefinition().name( "account" )
						.type( new GraphQLList( account() ) )
						.argument(
								newArgument().name( "id" )
										.description( "id of account" )
										.type( GraphQLLong )
										.build() )
						.dataFetcher( accountFetchers.getAccounts() )
						.build() )
				.build();
	}

	private GraphQLObjectType profile() {
		return newObject().name( "Profile" )
				.field( newFieldDefinition().name( "id" )
						.type( new GraphQLNonNull( GraphQLLong ) )
						.build() )
				.field( newFieldDefinition().name( "holder" )
						.type( new GraphQLNonNull( new GraphQLList( accountHolder() ) ) )
						.build() )
				.field( newFieldDefinition().name( "accounts" )
						.type( new GraphQLList( account() ) )
						.build() )
				.build();
	}

	private GraphQLObjectType accountHolder() {
		return newObject().name( "AccountHolder" )
				.field( newFieldDefinition().name( "id" )
						.type( new GraphQLNonNull( GraphQLLong ) )
						.build() )
				.field( newFieldDefinition().name( "firstName" ).type( GraphQLString ).build() )
				.field( newFieldDefinition().name( "lastName" ).type( GraphQLString ).build() )
				.build();
	}

	private GraphQLObjectType account() {
		return newObject().name( "Account" )
				.field( newFieldDefinition().name( "id" )
						.type( new GraphQLNonNull( GraphQLLong ) )
						.build() )
				.field( newFieldDefinition().name( "type" )
						.type( new GraphQLNonNull( GraphQLString ) )
						.build() )
				.field( newFieldDefinition().name( "balance" ).type( GraphQLBigDecimal ).build() )
				.build();
	}

}
