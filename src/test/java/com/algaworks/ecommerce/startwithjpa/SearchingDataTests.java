package com.algaworks.ecommerce.startwithjpa;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class SearchingDataTests extends EntityManagerTests {
	@Test
	public void searchProductWithId1UsingFind() {
		Product expectedProduct = new Product();
		expectedProduct.setId(UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));

		Product actualProduct = entityManager.find(Product.class, expectedProduct.getId());

		Assertions.assertEquals(expectedProduct, actualProduct);
	}
	
	@Test
	public void searchProductWithId1UsingGetReference() {
		Product expectedProduct = new Product();
		expectedProduct.setId(UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		// Return a proxy and select is applied when the properties will used
		Product actualProduct = entityManager.getReference(Product.class, expectedProduct.getId());

		Assertions.assertEquals(expectedProduct, actualProduct);
	}
	
	@Test
	public void searchProductWithId1AndRefreshReference() {
		Product actualProduct = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		actualProduct.setName("Microphone Samson");
		
		// Do select to refresh data from database
		entityManager.refresh(actualProduct);
		
		Assertions.assertEquals("Kindle", actualProduct.getName());
	}
}
