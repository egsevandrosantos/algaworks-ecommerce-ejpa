package com.algaworks.ecommerce.startwithjpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class SearchingDataTests extends EntityManagerTests {
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
