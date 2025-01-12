package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class HibridNamedDynamicQueryTests extends EntityManagerTests {
	@BeforeAll
	public static void beforeAll() {
		if (entityManagerFactory == null) {
			EntityManagerTests.beforeAll();
		}

		try (EntityManager localEM = entityManagerFactory.createEntityManager()) {
			String jpql = "SELECT c FROM Category c";
			
			TypedQuery<Category> query = localEM.createQuery(jpql, Category.class);
			
			entityManagerFactory.addNamedQuery("Category.findAll", query);
		}
	}
	
	@Test 
	public void test() {
		TypedQuery<Category> query = entityManager.createNamedQuery("Category.findAll", Category.class);
		
		List<Category> categories = query.getResultList();
		
		Assertions.assertFalse(categories.isEmpty());
	}
}
