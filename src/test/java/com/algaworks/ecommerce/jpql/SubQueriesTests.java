package com.algaworks.ecommerce.jpql;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class SubQueriesTests extends EntityManagerTests {
	@Test
	public void testSubQuery() {
		// Expensive products
		// String jpql = "SELECT MAX(p1.price) FROM Product p1"; // Get max price
		/*String jpql = """
			SELECT p.name, p.price
			FROM Product p
			WHERE p.price = (SELECT MAX(p1.price) FROM Product p1)
		""";*/
		
		// Order greater than average sales
		// String jpql = "SELECT AVG(o1.total) FROM Order o1"; // Get average
		/*String jpql = """
			SELECT o.total
			FROM Order o
			WHERE o.total > (SELECT AVG(o1.total) FROM Order o1)
		""";*/
		
		// Get better clients (buy more than 20)
		// String jpql = "SELECT c.name, SUM(o.total) FROM Order o JOIN o.client c GROUP BY c.id"; // Get total by client
		/*String jpql = """
			SELECT c.name
			FROM Client c
			WHERE (SELECT SUM(o1.total) FROM c.orders o1) > 20
		""";*/
		String jpql = """
			SELECT c.name
			FROM Client c
			WHERE (SELECT SUM(o1.total) FROM Order o1 WHERE o1.client = c) > 20
		""";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
	
	@Test
	public void testSubqueryWithIn() {
		/*String jpql = """
			SELECT DISTINCT o.id
			FROM Order o
			JOIN o.items i
			JOIN i.product p
			WHERE p.price > 100
		""";*/
		String jpql = """
			SELECT o.id
			FROM Order o
			WHERE o.id IN (
				SELECT o1.id
				FROM Order o1
				JOIN o1.items i1
				JOIN i1.product p1
				WHERE p1.price > 100
			)
		""";
			
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
