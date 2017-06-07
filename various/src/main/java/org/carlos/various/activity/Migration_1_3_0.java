package org.carlos.various.activity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Migration_1_3_0 {
	public static void main( String[] args ) throws IOException, JSONException {

		Map<String, BigDecimal> salaries = salaries();

		Map<String, String> activities = activities();
		Map<String, String> updatedActivities = updateActivities( activities, salaries );
		printUpdateActivities( updatedActivities );
		System.out.println();

		Map<String, String> news = news();
		Map<String, String> updatedNews = updateNews( news, salaries );
		printUpdateNews( updatedNews );
		System.out.println();

	}

	private static Map<String, BigDecimal> salaries() throws IOException {
		FileReader source = new FileReader( "/Users/carlos/Desktop/game_id_player_id_salary.csv" );

		try ( BufferedReader reader = new BufferedReader( source ) ) {

			return reader.lines()
					.skip( 1 )
					.map( line -> Arrays.asList( line.split( ";" ) ) )
					.collect( Collectors.toMap(
							strings -> game_id_player_id( Long.parseLong( strings.get( 0 ) ),
									Long.parseLong( strings.get( 1 ) ) ),
							strings -> new BigDecimal( strings.get( 2 ) ) ) );

		}
	}

	private static Map<String, String> activities() throws IOException {
		FileReader source = new FileReader( "/Users/carlos/Desktop/activities_to_update.csv" );

		try ( BufferedReader reader = new BufferedReader( source ) ) {

			return reader.lines().map( line -> Arrays.asList( line.split( ";" ) ) ).collect(
					Collectors.toMap( strings -> strings.get( 0 ), strings -> strings.get( 1 ) ) );

		}
	}

	private static Map<String, String> updateActivities( Map<String, String> activities,
			Map<String, BigDecimal> salaries ) throws JSONException {

		Map<String, String> activitiesUpdate = new HashMap<>();

		for ( Entry<String, String> entry : activities.entrySet() ) {

			JSONObject activityObject = new JSONObject( entry.getValue() );

			long gameId = activityObject.getLong( "gameId" );

			JSONArray highestScoringPicks = activityObject.optJSONArray( "highestScoringPicks" );
			if ( highestScoringPicks != null ) {
				JSONObject highestScoringPicks0 = highestScoringPicks.getJSONObject( 0 );

				activityObject.put( "highestScoringPick", highestScoringPicks0 );
				activityObject.remove( "highestScoringPicks" );
			}

			JSONArray bestValuePicks = activityObject.optJSONArray( "bestValuePicks" );
			if ( bestValuePicks != null ) {
				JSONObject bestValuePicks0 = bestValuePicks.getJSONObject( 0 );

				activityObject.put( "bestValuePick", bestValuePicks0 );
				activityObject.remove( "bestValuePicks" );
			}

			JSONObject bestValuePick = activityObject.optJSONObject( "bestValuePick" );
			if ( bestValuePick != null ) {

				if ( highestScoringPicks == null && bestValuePicks == null && bestValuePick
						.has( "playerScore" ) && bestValuePick.has( "playerValue" ) )
					continue;

				long playerId = bestValuePick.getLong( "playerId" );
				double playerStat = bestValuePick.getDouble( "playerStat" );

				BigDecimal salary = salaries.get( game_id_player_id( gameId, playerId ) );
				BigDecimal playerScore = calculateScore( salary, playerStat );

				bestValuePick.put( "playerScore", playerScore );
				bestValuePick.put( "playerValue", playerStat );

			}

			activitiesUpdate.put( entry.getKey(), activityObject.toString() );

		}

		return activitiesUpdate;
	}

	private static void printUpdateActivities( Map<String, String> updatedActivities ) {

		List<String> updatedActivitiesKeySet = updatedActivities.keySet()
				.stream()
				.sorted()
				.collect( Collectors.toList() );

		for ( String updatedActivitiesKey : updatedActivitiesKeySet ) {
			System.out.println( new StringBuilder().append( "UPDATE activity SET data = '" )
					.append( updatedActivities.get( updatedActivitiesKey ) )
					.append( "' WHERE hash = '" )
					.append( updatedActivitiesKey )
					.append( "';" )
					.toString() );
		}

	}

	private static Map<String, String> news() throws IOException {
		FileReader source = new FileReader( "/Users/carlos/Desktop/news_to_update.csv" );

		try ( BufferedReader reader = new BufferedReader( source ) ) {

			return reader.lines()
					.skip( 1 )
					.map( line -> Arrays.asList( line.split( ";" ) ) )
					.collect( Collectors.toMap( strings -> strings.get( 0 ),
							strings -> strings.get( 1 ) ) );

		}
	}

	private static Map<String, String> updateNews( Map<String, String> news,
			Map<String, BigDecimal> salaries ) throws JSONException {
		Map<String, String> updatedNews = new HashMap<>();

		for ( Entry<String, String> entry : news.entrySet() ) {

			JSONObject newsObject = new JSONObject( entry.getValue() );

			long gameId = newsObject.getLong( "gameId" );

			JSONObject newsStatObject = newsObject.optJSONObject( "stat" );
			if ( newsStatObject == null )
				continue;

			if ( newsStatObject.has( "playerScore" ) && newsStatObject.has( "playerValue" ) )
				continue;

			long playerId = newsStatObject.getLong( "playerId" );
			double playerStat = newsStatObject.getDouble( "playerStat" );

			BigDecimal salary = salaries.get( game_id_player_id( gameId, playerId ) );
			BigDecimal playerScore = calculateScore( salary, playerStat );

			newsStatObject.put( "playerScore", playerScore );
			newsStatObject.put( "playerValue", playerStat );

			updatedNews.put( entry.getKey(), newsObject.toString() );
		}

		return updatedNews;
	}

	private static void printUpdateNews( Map<String, String> updatedNews ) {

		List<String> updatedNewsKeySet = updatedNews.keySet()
				.stream()
				.sorted()
				.collect( Collectors.toList() );

		for ( String updatedNewsKey : updatedNewsKeySet ) {
			System.out.println( new StringBuilder().append( "UPDATE news SET context = '" )
					.append( updatedNews.get( updatedNewsKey ) )
					.append( "' WHERE id = " )
					.append( updatedNewsKey )
					.append( ";" )
					.toString() );
		}

	}

	private static BigDecimal calculateScore( BigDecimal salary, double playerStat ) {
		return salary.multiply( BigDecimal.valueOf( playerStat ) )
				.divide( BigDecimal.valueOf( 1000 ) )
				.setScale( 2, RoundingMode.HALF_DOWN );
	}

	private static String game_id_player_id( long gameId, long playerId ) {
		return new StringBuilder().append( gameId ).append( "_" ).append( playerId ).toString();
	}
}
