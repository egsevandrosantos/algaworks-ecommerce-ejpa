package com.algaworks.ecommerce.startwithjpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.PrimaryKeyStrategy;

@Deprecated
public class PrimaryKeyStrategyTests extends EntityManagerTests {
	@Test
	public void testPrimaryKeyStrategy() {
		PrimaryKeyStrategy primaryKeyStrategy = new PrimaryKeyStrategy();
		
		entityManager.getTransaction().begin();
		entityManager.persist(primaryKeyStrategy);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		PrimaryKeyStrategy actualPrimaryKeyStrategy = entityManager.find(PrimaryKeyStrategy.class, primaryKeyStrategy.getId());
		Assertions.assertEquals(primaryKeyStrategy, actualPrimaryKeyStrategy);
	}
}
