package com.algaworks.ecommerce.startwithjpa;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

public class TransactionalOperationsTests extends EntityManagerTests {
	@Test
	public void beginAndCloseTransactionWithCommit() {
		entityManager.getTransaction().begin();
		
		// Do operations
		//entityManager.persist(entity);
		//entityManager.merge(entity);
		//entityManager.remove(entity);
		
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void beginAndCloseTransactionWithRollback() {
		entityManager.getTransaction().begin();
		
		// Do operations
		//entityManager.persist(entity);
		//entityManager.merge(entity);
		//entityManager.remove(entity);
		
		entityManager.getTransaction().rollback();
	}
}
