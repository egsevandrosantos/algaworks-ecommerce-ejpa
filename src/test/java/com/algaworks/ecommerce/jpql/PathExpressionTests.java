package com.algaworks.ecommerce.jpql;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class PathExpressionTests extends EntityManagerTests {
	@Test
	public void testFindOrdersWithProduct() {
		// String jpql = "SELECT o FROM Order o WHERE o.items.id.productId = :productId"; // Not work
		String jpql = "SELECT o from Order o JOIN o.items i WHERE i.id.productId = :productId";
		// String jpql = "SELECT o FROM OrderItem oi JOIN oi.order o WHERE oi.id.productId = :productId";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("productId", UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testPathExpressions() {
		// String jpql = "SELECT o FROM Order o INNER JOIN o.client c WHERE c.name = 'João da Silva'";
		String jpql = "SELECT o FROM Order o WHERE o.client.name = 'João da Silva'"; // o.client.name = PATH EXPRESSION
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
