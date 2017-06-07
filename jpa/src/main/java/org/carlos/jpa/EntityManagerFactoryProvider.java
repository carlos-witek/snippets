package org.carlos.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;

public class EntityManagerFactoryProvider {
	private static final int MIN_POOL_SIZE = 10;
	private static final int MAX_POOL_SIZE = 100;

	private static final HibernatePersistenceProvider persistenceProvider = new HibernatePersistenceProvider();

	public static EntityManagerFactory get( String persistenceUnitName,
			Connection connectionInfo ) {
		return get( persistenceUnitName, connectionInfo, MIN_POOL_SIZE, MAX_POOL_SIZE );
	}

	public static EntityManagerFactory get( String persistenceUnitName,
			Connection connectionInfo, int minPoolSize, int maxPoolSize ) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put( "javax.persistence.jdbc.driver", connectionInfo.getDriverName() );
		properties.put( "javax.persistence.jdbc.url", connectionInfo.getUrl() );
		properties.put( "javax.persistence.jdbc.user", connectionInfo.getUserName() );
		properties.put( "javax.persistence.jdbc.password", connectionInfo.getPassword() );

		properties.put( "hibernate.show_sql", String.valueOf( connectionInfo.getDebug() ) );

		// configure connection pool
		properties.put( "hibernate.c3p0.min_size", Integer.toString( minPoolSize ) );
		properties.put( "hibernate.c3p0.max_size", Integer.toString( maxPoolSize ) );
		properties.put( "hibernate.c3p0.max_statements",
				Integer.toString( connectionInfo.getMaxStatements() ) );
		properties.put( "hibernate.c3p0.maxStatementsPerConnection",
				Integer.toString( connectionInfo.getMaxStatementsPerConnection() ) );

		properties.put( "hibernate.c3p0.idle_test_period",
				Integer.toString( connectionInfo.getIdleConnectionTestPeriod() ) );
		properties.put( "hibernate.c3p0.preferredTestQuery",
				connectionInfo.getConnectionTestQuery() );

		properties.put( "hibernate.c3p0.acquireRetryAttempts",
				Integer.toString( connectionInfo.getAcquireRetryAttempts() ) );
		properties.put( "hibernate.c3p0.acquireRetryDelay",
				Integer.toString( connectionInfo.getAcquireRetryDelay() ) );
		properties.put( "hibernate.c3p0.breakAfterAcquireFailure",
				Boolean.toString( connectionInfo.isBreakAfterAcquireFailure() ) );

		EntityManagerFactory entityManagerFactory = persistenceProvider.createEntityManagerFactory(
				persistenceUnitName, properties );

		return entityManagerFactory;
	}
}
