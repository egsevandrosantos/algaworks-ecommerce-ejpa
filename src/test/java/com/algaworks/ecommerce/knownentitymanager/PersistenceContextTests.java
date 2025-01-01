package com.algaworks.ecommerce.knownentitymanager;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class PersistenceContextTests extends EntityManagerTests {
	@Test
	public void testPersistenceContext() {
		// Managed and in persistence context
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		Product newProductPersist = new Product();
		newProductPersist.setName("Caneca para café");
		newProductPersist.setDescription("");
		
		Product newProductMerge = new Product();
		newProductMerge.setName("Caneca para chá");
		newProductMerge.setDescription("");
		
		entityManager.getTransaction().begin();
		
		// JPA listen this change (dirty checking - object in persistence context) and if has a transaction the update is executed
		product.setPrice(new BigDecimal("100.00"));
		
		// newProductPersist to persistence context
		entityManager.persist(newProductPersist);
		
		// newProductMerge to persistence context
		newProductMerge = entityManager.merge(newProductMerge);
		
		// JPA listen this change (dirty checking - object in persistence context) and if has a transaction the update is executed
		newProductPersist.setDescription("Caneca para café");
		newProductMerge.setDescription("Caneca para chá");

		entityManager.getTransaction().commit();
	}
}
