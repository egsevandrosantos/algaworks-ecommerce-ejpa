package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class LogicalOperatorTests extends EntityManagerTests {
	@Test
	public void testAndOrLogicalOperator() {
		// String jpql = "SELECT o FROM Order o WHERE o.total > 1 AND o.status = 'WAITING' AND o.client.id = :clientId";
		// String jpql = "SELECT o FROM Order o WHERE o.status = 'WAITING' OR o.status = 'PAID'";
		String jpql = "SELECT o FROM Order o WHERE o.total > 1 AND (o.status = 'WAITING' OR o.status = 'PAID')";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("clientId", UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
