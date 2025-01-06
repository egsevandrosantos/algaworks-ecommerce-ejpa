package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;

import jakarta.persistence.TypedQuery;

public class PaginationJpqlTests extends EntityManagerTests {
	@Test
	public void testPagination() {
		String jpql = "SELECT c FROM Category c ORDER BY c.name";
		
		TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
		query.setFirstResult(0); // Calculate page = maxResults * (page - 1)
		query.setMaxResults(2);
		
		List<Category> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
