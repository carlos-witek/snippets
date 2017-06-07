package org.carlos.jpa;

public class Connection {

	private final String driverName;
	private final String url;
	private final String userName;
	private final String password;

	private final boolean debug;

	private int idleConnectionTestPeriod;
	private String connectionTestQuery;
	private int maxStatements;
	private int maxStatementsPerConnection;
	private int acquireRetryAttempts;
	private int acquireRetryDelay;
	private boolean breakAfterAcquireFailure;

	public Connection( String driverName, String url, String userName, String password,
			boolean debug ) {
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.password = password;

		this.debug = debug;

		setMaxStatements( 500 );
		setMaxStatementsPerConnection( 25 );
		setAcquireRetryAttempts( 3 );
		setAcquireRetryDelay( 1000 );
		setBreakAfterAcquireFailure( false );
		setIdleConnectionTestPeriod( 3600 );
		setConnectionTestQuery( "SELECT 1" );
	}

	/**
	 * Fully qualified driver class name
	 *
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Connection Url to the database
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Username on the database
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Password for the username
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Log with debug level
	 *
	 * @return debug true if debug level, false otherwise
	 */
	public boolean getDebug() {
		return debug;
	}

	/**
	 * Period in seconds to perform a safety test to the pool idle connections
	 *
	 * @return test period in seconds
	 */
	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public void setIdleConnectionTestPeriod( int idleConnectionTestPeriod ) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	/**
	 * Test query to issue when a test is performed to the idle connections.
	 *
	 * @return test query
	 */
	public String getConnectionTestQuery() {
		return connectionTestQuery;
	}

	public void setConnectionTestQuery( String connectionTestQuery ) {
		this.connectionTestQuery = connectionTestQuery;
	}

	/**
	 * Max number of statements the pool will cache
	 *
	 * C3P0 recommendation: " If set, it should be a fairly large number, as
	 * each pooled Connection requires its own, distinct flock of cached
	 * statements. As a guide, consider how many distinct PreparedStatements are
	 * used frequently in your application, and multiply that number by
	 * maxPoolSize to arrive at an appropriate value"
	 *
	 * @return max number of cached statements
	 */
	public int getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements( int maxStatements ) {
		this.maxStatements = maxStatements;
	}

	/**
	 * Max number of statements the pool will cache per connection
	 *
	 * C3P0 recommendation: "If set, maxStatementsPerConnection should be set to
	 * about the number distinct PreparedStatements that are used frequently in
	 * your application, plus two or three extra so infrequently statements
	 * don't force the more common cached statements to be culled."
	 *
	 * @return max number of cached statements per connection
	 */
	public int getMaxStatementsPerConnection() {
		return maxStatementsPerConnection;
	}

	public void setMaxStatementsPerConnection( int maxStatementsPerConnection ) {
		this.maxStatementsPerConnection = maxStatementsPerConnection;
	}

	/**
	 * Number of retry attempts when the pool can't acquire a connection
	 *
	 * @return number of retry attempts
	 */
	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public void setAcquireRetryAttempts( int acquireRetryAttempts ) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	/**
	 * Period of time between retry attempts in milliseconds
	 *
	 * @return delay between retries in milliseconds
	 */
	public int getAcquireRetryDelay() {
		return acquireRetryDelay;
	}

	public void setAcquireRetryDelay( int acquireRetryDelay ) {
		this.acquireRetryDelay = acquireRetryDelay;
	}

	/**
	 * Stop if the pool can't acquire a connection
	 *
	 * @return true if stop in fail, false otherwise
	 */
	public boolean isBreakAfterAcquireFailure() {
		return breakAfterAcquireFailure;
	}

	public void setBreakAfterAcquireFailure( boolean breakAfterAcquireFailure ) {
		this.breakAfterAcquireFailure = breakAfterAcquireFailure;
	}

}
