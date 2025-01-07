package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class GroupByTests extends EntityManagerTests {
	@Test
	public void testGroupBy() {
		// Products by category:
		// String jpql = "SELECT c.name, COUNT(p.id) FROM Category c JOIN c.products p GROUP BY c.id";
		
		// Total sales by year-month:
		// String jpql = "SELECT CONCAT(YEAR(o.createdAt), ' ', FUNCTION('monthname', o.createdAt)), SUM(o.total) FROM Order o GROUP BY CONCAT(YEAR(o.createdAt), ' ', FUNCTION('monthname', o.createdAt))";
		
		// Total sales by category:
		String jpql = """
			SELECT
				c.name,
				SUM(i.productPrice * i.quantity)
			FROM OrderItem i
				JOIN i.product p
				JOIN p.categories c
			GROUP BY
				c.id
			ORDER BY
				c.name
		""";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(r -> System.out.println(r[0] + "  -->  " + r[1]));
	}
}
