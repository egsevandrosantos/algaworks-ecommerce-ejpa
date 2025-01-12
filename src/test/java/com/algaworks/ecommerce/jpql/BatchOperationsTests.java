package com.algaworks.ecommerce.jpql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.Query;

public class BatchOperationsTests extends EntityManagerTests {
	private final int LIMIT_TO_INSERT = 4;

	@Test
	public void testBatchInsertOperation() throws IOException {
		try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("products/import.txt")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			entityManager.getTransaction().begin();
			
			int countInsertions = 0;

			for (String line : reader.lines().collect(Collectors.toList())) {
				if (line.isBlank()) continue;
				
				String[] columns = line.split(";");

				Product product = new Product();
				product.setName(columns[0]);
				product.setDescription(columns[1]);
				product.setPrice(new BigDecimal(columns[2]));
				product.setCreatedAt(Instant.now());
				
				entityManager.persist(product);
				
				if (++countInsertions >= LIMIT_TO_INSERT) {
					entityManager.flush();
					entityManager.clear();
					
					countInsertions = 0;
					
					System.out.println("Memory cleared");
				}
			}
			
			entityManager.getTransaction().commit();
		}
	}
	
	@Test
	public void testBatchUpdateOperation() {
		// String jpql = "UPDATE Product p SET p.price = p.price + 1";
		String jpql = "UPDATE Product p SET p.price = p.price + (p.price * :tenPercent) WHERE EXISTS (SELECT 1 FROM p.categories c1 WHERE c1.id = :categoryId)";
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter("tenPercent", 10 / 100);
		query.setParameter("categoryId", UUID.fromString("65a38317-8d2b-43a9-ba84-0f6610bdc128"));
		
		entityManager.getTransaction().begin();
		int numbersAffected = query.executeUpdate();
		entityManager.getTransaction().commit();
		
		Assertions.assertTrue(numbersAffected != 0);
	}
}
