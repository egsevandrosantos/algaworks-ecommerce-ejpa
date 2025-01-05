package com.algaworks.ecommerce.jpql;

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
}
