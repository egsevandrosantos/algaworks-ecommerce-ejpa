package com.algaworks.ecommerce.relationship;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Product;

public class RelationshipManyToManyTests extends EntityManagerTests {
	@Test
	public void testProductsCategoriesRelationship() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		Category category = entityManager.find(Category.class, UUID.fromString("d9e5d6f8-6605-4dcd-a21a-3839407a0a1f"));
		
		entityManager.getTransaction().begin();
		// Only owner of relationship save them
		product.setCategories(List.of(category)); // Owner (list of categories is saved)
		// category.setProducts(List.of(product)); // Non-owner (list of products is not saved)
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category actualCategory = entityManager.find(Category.class, category.getId());
		Assertions.assertTrue(category.fullEquals(actualCategory) && actualCategory.getProducts().size() == 1 && Objects.equals(product.getId(), actualCategory.getProducts().get(0).getId()));
	}
}
