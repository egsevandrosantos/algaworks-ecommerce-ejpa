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
	
	@Test
	public void testLeftJoin() {
		// LEFT JOIN = LEFT OUTER JOIN
		// String jpql = "SELECT o FROM Order o LEFT JOIN o.payment p"; // LEFT JOIN is ignored because 'p' is ignored
		// String jpql = "SELECT p FROM Payment p LEFT JOIN p.order o"; // LEFT JOIN is ignored because 'o' is ignored
		// String jpql = "SELECT o, p FROM Order o LEFT JOIN o.payment p"; // LEFT JOIN in final query
		// String jpql = "SELECT o FROM Order o LEFT JOIN o.payment p ON p.status = 'PROCESSING'";
		// Is different (because ON is executed and after that execute LEFT JOIN / because LEFT JOIN is executed and after that execute WHERE):
		String jpql = "SELECT o FROM Order o LEFT JOIN o.payment p WHERE p.status = 'PROCESSING'";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testJoinFetch() {
		// String jpql = "SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id";
		// String jpql = "SELECT o FROM Order o"; // N+1 problem
		String jpql = "SELECT o FROM Order o JOIN FETCH o.payment JOIN FETCH o.client LEFT JOIN FETCH o.invoice"; // Without N+1
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("id", UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
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
