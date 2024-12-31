package com.algaworks.ecommerce.advancedmapping;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class ElementCollectionTests extends EntityManagerTests {
	@Test
	public void testElementCollectionTagsProduct() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		entityManager.getTransaction().begin();
		product.setTags(List.of("ebook", "digital-book"));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertTrue(actualProduct.getTags() != null && !actualProduct.getTags().isEmpty());
	}
}
