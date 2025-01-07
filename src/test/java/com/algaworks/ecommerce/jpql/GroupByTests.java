package com.algaworks.ecommerce.jpql;

import java.util.Arrays;
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
	
	@Test
	public void testTotalSalesGroupedByClient() {
		String jpql = """
			SELECT
				c.name,
				SUM(o.total)
			FROM Order o
				JOIN o.client c
			GROUP BY c.id
			ORDER BY c.name
		""";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
	
	@Test
	public void testTotalSalesGroupedByDayAndCategory() {
		String jpql = """
			SELECT
				YEAR(o.createdAt),
				FUNCTION('monthname', o.createdAt),
				DAY(o.createdAt),
				c.name,
				SUM(i.productPrice * i.quantity)
			FROM Order o
				JOIN o.items i
				JOIN i.product p
				JOIN p.categories c
			GROUP BY
				YEAR(o.createdAt),
				MONTH(o.createdAt),
				FUNCTION('monthname', o.createdAt),
				DAY(o.createdAt),
				c.id
			ORDER BY
				YEAR(o.createdAt),
				MONTH(o.createdAt),
				DAY(o.createdAt),
				c.name
		""";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
