package com.algaworks.ecommerce.advancedmapping;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class ColumnDetailTests extends EntityManagerTests {
	@Test
	public void testProductDoNotInsertUpdatedAt() {
		Product product = new Product();
		product.setName("Smartphone keyboard");
		product.setDescription("O mais confortável");
		product.setPrice(BigDecimal.ONE);
		product.setCreatedAt(Instant.now());
		product.setUpdatedAt(Instant.now()); // insertable = false
		
		entityManager.getTransaction().begin();
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertNull(actualProduct.getUpdatedAt());
	}
	
	@Test
	public void testProductDoNotUpdateCreatedAt() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		Instant now = Instant.now();
		
		entityManager.getTransaction().begin();
		product.setPrice(BigDecimal.TEN);
		product.setCreatedAt(now); // updatable = false
		entityManager.merge(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertNotEquals(now, actualProduct.getCreatedAt());
	}
}
