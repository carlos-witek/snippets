package org.carlos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EntityManagerFactoryHelper {

	public static final <T> T getEntity( EntityManagerFactory entityManagerFactory,
			Class<T> entityClass, Object entityId ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			return entityManager.find( entityClass, entityId );
		} finally {
			entityManager.close();
		}
	}

	public static final <T> List<T> getEntities( EntityManagerFactory entityManagerFactory,
			Class<T> entityClass ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery( entityClass );
			criteriaQuery.from( entityClass );
			return entityManager.createQuery( criteriaQuery ).getResultList();
		} finally {
			entityManager.close();
		}
	}

	public static final <T> List<T> getEntities( EntityManagerFactory entityManagerFactory,
			String query, Class<T> resultClass ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			return entityManager.createQuery( query, resultClass ).getResultList();
		} finally {
			entityManager.close();
		}
	}

	public static void createEntities( EntityManagerFactory entityManagerFactory,
			Object... entities ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			for ( Object entity : entities )
				entityManager.persist( entity );
			transaction.commit();
		} catch ( RuntimeException e ) {
			if ( transaction.isActive() )
				transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	public static void updateEntities( EntityManagerFactory entityManagerFactory,
			Object... entities ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			for ( Object entity : entities )
				entityManager.merge( entity );
			transaction.commit();
		} catch ( RuntimeException e ) {
			if ( transaction.isActive() )
				transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	public static void deleteEntities( EntityManagerFactory entityManagerFactory,
			Class<?>... entityClasses ) {
		for ( Class<?> entityClass : entityClasses )
			deleteEntities( entityManagerFactory, entityClass );
	}

	public static <T> void deleteEntities( EntityManagerFactory entityManagerFactory,
			Class<T> entityClass ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaDelete<T> criteriaQuery = criteriaBuilder.createCriteriaDelete( entityClass );
			criteriaQuery.from( entityClass );
			entityManager.createQuery( criteriaQuery ).executeUpdate();
			transaction.commit();
		} catch ( RuntimeException e ) {
			if ( transaction.isActive() )
				transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	public static <T> void deleteEntity( EntityManagerFactory entityManagerFactory,
			Class<T> entityClass, Object id ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaDelete<T> criteriaQuery = criteriaBuilder.createCriteriaDelete( entityClass );
			Root<T> root = criteriaQuery.from( entityClass );
			criteriaQuery.where( criteriaBuilder.equal( root.get( "id" ), id ) );
			entityManager.createQuery( criteriaQuery ).executeUpdate();
			transaction.commit();
		} catch ( RuntimeException e ) {
			if ( transaction.isActive() )
				transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	public static void resetAutoIncrement( EntityManagerFactory entityManagerFactory, String table,
			String column ) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.createNativeQuery(
					String.format( "ALTER TABLE %s ALTER COLUMN %s RESTART WITH 1", table, column ) )
					.executeUpdate();
			transaction.commit();
		} finally {
			entityManager.close();
		}
	}
}
