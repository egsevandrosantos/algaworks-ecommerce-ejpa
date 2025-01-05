package com.algaworks.ecommerce.jpql;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class ConditionalExpressionsTests extends EntityManagerTests {
	@Test
	public void testLikeOperator() {
		// String jpql = "SELECT c FROM Client c WHERE c.name LIKE :name";
		// String jpql = "SELECT c FROM Client c WHERE c.name LIKE CONCAT(:name, '%')";
		// String jpql = "SELECT c FROM Client c WHERE c.name LIKE CONCAT('%', :name)";
		String jpql = "SELECT c FROM Client c WHERE c.name LIKE CONCAT('%', :name, '%')";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("name", "João%");
		// query.setParameter("name", "João");
		// query.setParameter("name", "Silva");
		query.setParameter("name", "o");
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testIsNullAndIsNotNull() {
		String jpql = "SELECT p FROM Product p WHERE p.photo IS NULL";
		// String jpql = "SELECT p FROM Product p WHERE p.photo IS NOT NULL";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testIsEmptyAndIsNotEmpty() {
		// Is empty is for collection
		String jpql = "SELECT p FROM Product p WHERE p.categories IS EMPTY"; // WHERE NOT EXISTS
		// String jpql = "SELECT p FROM Product p WHERE p.categories IS NOT EMPTY"; // WHERE EXISTS
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGreaterThanEqualAndLessThanEqual() {
		// String jpql = "SELECT p FROM Product p WHERE p.price > :price";
		// String jpql = "SELECT p FROM Product p WHERE p.price >= :price";
		// String jpql = "SELECT p FROM Product p WHERE p.price < :price";
		// String jpql = "SELECT p FROM Product p WHERE p.price <= :price";
		String jpql = "SELECT p FROM Product p WHERE p.price >= :priceInterval1 AND p.price <= :priceInterval2";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("price", new BigDecimal("499.00"));
		query.setParameter("priceInterval1", new BigDecimal("400.00"));
		query.setParameter("priceInterval2", new BigDecimal("1500.00"));
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testFindOrders2DaysAgo() {
		Instant dateInterval1 = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusDays(2)
			.with(LocalTime.of(0, 0, 0))
			.toInstant();
		Instant dateInterval2 = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.with(LocalTime.of(23, 59, 59))
			.toInstant();
		
		String jpql = "SELECT o FROM Order o WHERE o.createdAt >= :dateInterval1 AND o.createdAt <= :dateInterval2";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("dateInterval1", dateInterval1);
		query.setParameter("dateInterval2", dateInterval2);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
