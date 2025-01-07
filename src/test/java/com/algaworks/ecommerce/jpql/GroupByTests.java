package com.algaworks.ecommerce.jpql;

import java.time.Instant;
import java.time.ZoneOffset;
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
		Instant actualDateSub3Months = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusMonths(3)
			.toInstant();
		
		String jpql = """
			SELECT
				c.name,
				SUM(o.total)
			FROM Order o
				JOIN o.client c
			WHERE
				o.createdAt >= :actualDateSub3Months
			GROUP BY c.id
			ORDER BY c.name
		""";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("actualDateSub3Months", actualDateSub3Months);
		
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
	
	@Test
	public void testGroupByWithWhere() {
		// Products by category:
		// String jpql = "SELECT c.name, COUNT(p.id) FROM Category c JOIN c.products p GROUP BY c.id";
		
		// Total sales by year-month:
		/*String jpql = """
			SELECT
				CONCAT(YEAR(o.createdAt), ' ', FUNCTION('monthname', o.createdAt)),
				SUM(o.total)
			FROM Order o
			WHERE
				YEAR(o.createdAt) = YEAR(current_date)
			GROUP BY
				CONCAT(YEAR(o.createdAt), ' ', FUNCTION('monthname', o.createdAt))
		""";*/
		
		// Total sales by category:
		String jpql = """
			SELECT
				c.name,
				SUM(i.productPrice * i.quantity)
			FROM OrderItem i
				JOIN i.order o
				JOIN i.product p
				JOIN p.categories c
			WHERE
				YEAR(o.createdAt) = YEAR(current_date)
				AND MONTH(o.createdAt) = MONTH(current_date)
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
	public void testGroupByWithHaving() {
		// Having only accept aggregate functions and properties in group by
		// Total sales by category with (having) most sales (greater than 10 in sales):
		/*String jpql = """
			SELECT
				c.name,
				SUM(i.productPrice * i.quantity)
			FROM OrderItem i
				JOIN i.product p
				JOIN p.categories c
			GROUP BY c.id
			HAVING SUM(i.productPrice * i.quantity) > 10
			ORDER BY c.name
		""";*/
		/*String jpql = """
			SELECT
				c.name,
				SUM(i.productPrice * i.quantity),
				AVG(i.productPrice * i.quantity)
			FROM OrderItem i
				JOIN i.product p
				JOIN p.categories c
			GROUP BY c.id
			HAVING AVG(i.productPrice * i.quantity) > 10
			ORDER BY c.name
		""";*/
		String jpql = """
			SELECT
				c.name,
				SUM(i.productPrice * i.quantity),
				COUNT(c.id)
			FROM OrderItem i
				JOIN i.product p
				JOIN p.categories c
			GROUP BY c.id
			HAVING COUNT(c.id) > 1
			ORDER BY c.name
		""";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
