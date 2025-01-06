package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class StringFunctionsJqplTests extends EntityManagerTests {
	@Test
	public void testFunctions() {
		// concat, length, locate, substring, lower, upper, trim
		// String jpql = "SELECT c.name, CONCAT('Category: ', c.name, '.') FROM Category c";
		// String jpql = "SELECT c.name, LENGTH(c.name) FROM Category c";
		// String jpql = "SELECT c.name, LOCATE('a', c.name) FROM Category c"; // Equals indexOf, if not find return 0, first index = 1
		// String jpql = "SELECT c.name, SUBSTRING(c.name, 1, 2) FROM Category c"; // Substring from index 1 (first index) get 2 characters
		// String jpql = "SELECT c.name, LOWER(c.name) FROM Category c";
		// String jpql = "SELECT c.name, UPPER(c.name) FROM Category c";
		// String jpql = "SELECT c.name, TRIM(c.name) FROM Category c";
		// String jpql = "SELECT c.name, LENGTH(c.name) FROM Category c WHERE LENGTH(c.name) > 10";
		String jpql = "SELECT c.name, SUBSTRING(c.name, 1, 1) FROM Category c WHERE SUBSTRING(c.name, 1, 1) = 'B'";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(r -> System.out.println(r[0] + "  -->  " + r[1]));
	}
}
