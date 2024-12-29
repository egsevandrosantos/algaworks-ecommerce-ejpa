package com.algaworks.ecommerce.knownentitymanager;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class CacheFirstLevel extends EntityManagerTests {
	@Test
	public void testCacheFirstLevel() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		System.out.println(product.getName());
		
		System.out.println("--------------------------------------------------");
		
		// This select is not executed because this product already loaded and was in memory
		product = entityManager.find(Product.class, product.getId());
		System.out.println(product.getName());
		
		Assertions.assertNotNull(product);
	}
}
