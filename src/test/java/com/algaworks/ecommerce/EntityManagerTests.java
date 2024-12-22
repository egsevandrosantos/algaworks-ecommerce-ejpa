package com.algaworks.ecommerce;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerTests {
	protected static EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	
	@BeforeAll
	public static void beforeAll() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
	}
	
	@AfterAll
	public static void afterAll() {
		entityManagerFactory.close();
	}
	
	@BeforeEach
	public void beforeEach() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterEach
	public void afterEach() {
		entityManager.close();
	}
}
