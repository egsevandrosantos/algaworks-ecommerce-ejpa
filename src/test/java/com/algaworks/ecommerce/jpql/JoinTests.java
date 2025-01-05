package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class JoinTests extends EntityManagerTests {
	@Test
	public void testInnerJoin() {
		// String jpql = "SELECT o FROM Order o JOIN o.payment p"; // JOIN = INNER JOIN
		// String jpql = "SELECT o, p FROM Order o JOIN o.payment p WHERE p.status = 'PROCESSING'"; // Projection with two objects is possible
		// String jpql = "SELECT o FROM Order o JOIN o.items i WHERE i.productPrice > 1.00"; // JOIN with list items and filter by price
		// String jpql = "SELECT o, i FROM Order o JOIN o.items i";
		// String jpql = "SELECT o, p, pay FROM Order o JOIN o.payment pay JOIN o.items i JOIN i.product p";
		// String jpql = "SELECT o, p, pay FROM Order o JOIN o.payment pay ON pay.status = 'PROCESSING' JOIN o.items i JOIN i.product p"; // Is possible add clauses to on
		// Equals:
		String jpql = "SELECT o, p, pay FROM Order o JOIN o.payment pay JOIN o.items i JOIN i.product p WHERE pay.status = 'PROCESSING'";
		
		// TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
