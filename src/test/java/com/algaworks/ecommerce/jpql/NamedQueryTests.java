package com.algaworks.ecommerce.jpql;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

public class NamedQueryTests extends EntityManagerTests {
	@Test
	public void testNamedQueryFindAll() {
		TypedQuery<Product> query = entityManager.createNamedQuery("Product.findAll", Product.class);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
	}
	
	@Test
	public void testNamedQueryFindByCategory() {
		TypedQuery<Product> query = entityManager.createNamedQuery("Product.findByCategory", Product.class);
		query.setParameter("categoryId", UUID.fromString("65a38317-8d2b-43a9-ba84-0f6610bdc128"));
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
	}
}
