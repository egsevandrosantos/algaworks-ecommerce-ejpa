package com.algaworks.ecommerce.jpql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

public class DynamicQueryTests extends EntityManagerTests {
	@Test
	public void testFindWithDynamicQuery() {
		Product product = new Product();
		product.setName("Kindle");
		
		List<Product> result = findByProduct(product);
		
		Assertions.assertFalse(result.isEmpty());
	}
	
	public List<Product> findByProduct(Product product) {
		List<String> instructions = new ArrayList<>(List.of("SELECT p FROM Product p WHERE 1 = 1"));
		Map<String, Object> parameters = new HashMap<>();
		
		if (product.getName() != null && !product.getName().isBlank()) {
			instructions.add("AND p.name LIKE CONCAT('%', :name, '%')");
			parameters.put("name", product.getName());
		}
		
		if (product.getDescription() != null && !product.getDescription().isBlank()) {
			instructions.add("AND p.description LIKE CONCAT('%', :description, '%')");
			parameters.put("description", product.getDescription());
		}
		
		TypedQuery<Product> query = entityManager.createQuery(String.join(" ", instructions), Product.class);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return query.getResultList();
	}
}
