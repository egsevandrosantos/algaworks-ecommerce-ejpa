package com.algaworks.ecommerce.criteria;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PaginationTests extends EntityManagerTests {
	@Test
	public void testPagination() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = criteriaQuery.from(Category.class);
		
		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
		
		TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0); // Calculate page = maxResults * (page - 1)
		query.setMaxResults(2);
		
		List<Category> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
