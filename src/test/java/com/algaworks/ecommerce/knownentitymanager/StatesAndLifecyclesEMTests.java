package com.algaworks.ecommerce.knownentitymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;

public class StatesAndLifecyclesEMTests extends EntityManagerTests {
	@Test
	public void testStatesAndLifecycles() {
		// Transient
		Category category = new Category();
		category.setName("Transient");
		
		// Managed
		entityManager.getTransaction().begin();
		entityManager.persist(category);
		entityManager.getTransaction().commit();
		
		category.setName("Persisted");
		// Managed
		entityManager.getTransaction().begin();
		category = entityManager.merge(category);
		entityManager.getTransaction().commit();
		
		// Removed
		entityManager.getTransaction().begin();
		entityManager.remove(category);
		entityManager.getTransaction().commit();
		
		category.setId(null); // New category after delete
		// Managed
		entityManager.getTransaction().begin();
		entityManager.persist(category);
		entityManager.getTransaction().commit();
		
		// Detached
		entityManager.clear();

		// Managed
		category = entityManager.find(Category.class, category.getId());
		
		// Detached
		entityManager.detach(category);
		
		Assertions.assertNotNull(category);
	}
}
