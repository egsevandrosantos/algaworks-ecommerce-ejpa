package com.algaworks.ecommerce;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import jakarta.persistence.EntityManager;

public class EntityManagerTests extends EntityManagerFactoryTests {
	protected EntityManager entityManager;
	
	@BeforeEach
	public void beforeEach() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterEach
	public void afterEach() {
		entityManager.close();
	}
}
