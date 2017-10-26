package org.carlos.guice.common;

import java.util.UUID;

public class Connections {
	private static final String HSQLDB_DRIVER_NAME = "org.hsqldb.jdbcDriver";
	private static final String HSQLDB_JDBC_URL = "jdbc:hsqldb:mem:%s;sql.syntax_mys=true;shutdown=true";
	private static final String HSQLDB_JDBC_URL_SQLLOG = HSQLDB_JDBC_URL + ";hsqldb.sqllog=3";

	public static final Connection hsqldb( boolean debug ) {
		return hsqldb( debug, false );
	}

	public static final Connection hsqldb( boolean debug, boolean sqllog ) {
		String urlTemplate = sqllog ? HSQLDB_JDBC_URL_SQLLOG : HSQLDB_JDBC_URL;
		String url = String.format( urlTemplate, UUID.randomUUID().toString() );
		return new Connection( HSQLDB_DRIVER_NAME, url, "sa", "", debug );
	}

	private final static String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final static String MYSQL_JDBC_URL = "jdbc:mysql://%s/%s?autoReconnect=%b&useCompression=%b";

	public static final Connection mysql( String host, String dbName, String userName,
			String password ) {
		return mysql( host, dbName, userName, password, false, false, false );
	}

	public static final Connection mysql( String host, String dbName, String userName,
			String password, boolean debug ) {
		return mysql( host, dbName, userName, password, debug, false, false );
	}

	public static final Connection mysql( String host, String dbName, String userName,
			String password, boolean debug, boolean autoReconnect ) {
		return mysql( host, dbName, userName, password, debug, autoReconnect, false );
	}

	public static final Connection mysql( String host, String dbName, String userName,
			String password, boolean debug, boolean autoReconnect, boolean useCompression ) {
		String url = String.format( MYSQL_JDBC_URL, host, dbName, autoReconnect, useCompression );
		return new Connection( MYSQL_DRIVER_NAME, url, userName, password, debug );
	}
}
