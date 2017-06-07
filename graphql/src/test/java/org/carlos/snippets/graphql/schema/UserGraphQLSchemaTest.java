package org.carlos.snippets.graphql.schema;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.batched.BatchedExecutionStrategy;

import org.carlos.snippets.graphql.fetcher.AccountFetchers;
import org.carlos.snippets.graphql.fetcher.AccountHolderFetchers;
import org.carlos.snippets.graphql.fetcher.ProfileFetchers;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UserGraphQLSchemaTest {

	@Test
	public void testUsers() throws Exception {
		// set-up
		GraphQL graphQL = initGraphQL();

		// test
		{
			List<Map<?, ?>> users = execute( graphQL, "user", "{user{id,email}}" );
			assertEquals( 2, users.size() );
			assertEquals( "[{id=1, email=user1@users.com}, {id=2, email=user2@users.com}]",
					users.toString() );
		}

		// test with userProfile
		{
			List<Map<?, ?>> users = execute( graphQL, "user",
					"{user{id,email,userProfile{firstName,lastName}}}" );
			assertEquals( 2, users.size() );
			assertEquals(
					"[{id=1, email=user1@users.com, userProfile={firstName=user1, lastName=doe}}, {id=2, email=user2@users.com, userProfile={firstName=user2, lastName=doe}}]",
					users.toString() );
		}

	}

	@Test
	public void testUser() throws Exception {
		// set-up
		GraphQL graphQL = initGraphQL();

		// test
		{
			List<Map<?, ?>> users = execute( graphQL, "user", "{user(id:1){id,email}}" );
			assertEquals( 1, users.size() );
			assertEquals( "{id=1, email=user1@users.com}", users.get( 0 ).toString() );
		}

		// test
		{
			List<Map<?, ?>> users = execute( graphQL, "user", "{user(id:2){id,email}}" );
			assertEquals( 1, users.size() );
			assertEquals( "{id=2, email=user2@users.com}", users.get( 0 ).toString() );
		}

		// test with userProfile
		{
			List<Map<?, ?>> users = execute( graphQL, "user",
					"{user(id:1){id,email,userProfile{firstName,lastName}}}" );
			assertEquals( 1, users.size() );
			assertEquals(
					"{id=1, email=user1@users.com, userProfile={firstName=user1, lastName=doe}}",
					users.get( 0 ).toString() );
		}

		// test with mainAccountId and mainAccountBalance
		{
			List<Map<?, ?>> users = execute( graphQL, "user",
					"{user(id:1){id,email,mainAccountId,mainAccountBalance}}" );
			assertEquals( 1, users.size() );
			assertEquals( "{mainAccountBalance=0.00, id=1, email=user1@users.com, mainAccountId=1}",
					users.get( 0 ).toString() );
		}

	}

	@Test
	public void testAccounts() throws Exception {
		// set-up
		GraphQL graphQL = initGraphQL();

		// test
		List<Map<?, ?>> users = execute( graphQL, "account", "{account{id,balance}}" );
		assertEquals( 2, users.size() );
		assertEquals( "[{balance=0.00, id=1}, {balance=0.00, id=2}]", users.toString() );
	}

	@SuppressWarnings("unchecked")
	private List<Map<?, ?>> execute( GraphQL graphQL, String xxx, String graphQLQuery ) {
		ExecutionResult executionResult = graphQL.execute( graphQLQuery );
		Map<?, ?> executionResultData = (Map<?, ?>) executionResult.getData();
		System.out.println( "executionResultData " + executionResultData );
		System.out.println( "executionResultData " + executionResult.getErrors() );
		return (List<Map<?, ?>>) executionResultData.get( xxx );
	}

	private GraphQL initGraphQL() {
		AccountHolderFetchers userFetchers = new AccountHolderFetchers();
		ProfileFetchers userProfileFetchers = new ProfileFetchers();
		AccountFetchers accountFetchers = new AccountFetchers();
		UserGraphQLSchema schema = new UserGraphQLSchema( userFetchers,
				userProfileFetchers,
				accountFetchers );

		return new GraphQL( schema.getSchema(), new BatchedExecutionStrategy() );
	}
}
