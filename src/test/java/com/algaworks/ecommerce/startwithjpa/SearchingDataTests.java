package com.algaworks.ecommerce.startwithjpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SearchingDataTests {
	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
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
	
	@Test
	public void searchProductWithId1UsingFind() {
		Product expectedProduct = new Product();
		expectedProduct.setId(1);

		Product actualProduct = entityManager.find(Product.class, 1);

		Assertions.assertEquals(expectedProduct, actualProduct);
	}
	
	@Test
	public void searchProductWithId1UsingGetReference() {
		Product expectedProduct = new Product();
		expectedProduct.setId(1);
		
		// Return a proxy and select is applied when the properties will used
		Product actualProduct = entityManager.getReference(Product.class, 1);

		Assertions.assertEquals(expectedProduct, actualProduct);
	}
	
	@Test
	public void searchProductWithId1AndRefreshReference() {
		Product actualProduct = entityManager.find(Product.class, 1);
		actualProduct.setName("Microphone Samson");
		
		// Do select to refresh data from database
		entityManager.refresh(actualProduct);
		
		Assertions.assertEquals("Kindle", actualProduct.getName());
	}
}
