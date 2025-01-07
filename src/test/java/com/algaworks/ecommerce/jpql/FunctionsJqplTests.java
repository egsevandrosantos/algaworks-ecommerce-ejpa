package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;

public class FunctionsJqplTests extends EntityManagerTests {
	@Test
	public void testFunctionsString() {
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
	
	@Test
	public void testFunctionsDate() {
		// TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// String jpql = "SELECT current_date, current_time, current_timestamp FROM Order o"; // Date, Time, Date/Time with converted timezone
		// String jpql = "SELECT current_date, current_time, current_timestamp FROM Order o WHERE o.createdAt > current_date";
		// String jpql = "SELECT YEAR(current_timestamp), MONTH(current_timestamp), DAY(current_timestamp) FROM Order o";
		// String jpql = "SELECT YEAR(o.createdAt), MONTH(o.createdAt), DAY(o.createdAt) FROM Order o";
		String jpql = "SELECT HOUR(o.createdAt), MINUTE(o.createdAt), SECOND(o.createdAt) FROM Order o WHERE HOUR(o.createdAt) > 18";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(r -> System.out.println(r[0] + "  -->  " + r[1] + "  -->  " + r[2]));
	}
	
	@Test
	public void testFunctionsNumber() {
		// String jpql = "SELECT ABS(-10), ABS(10), ABS(10.9) FROM Order o"; // Get only number (absolute) -> 10, 10, 10.9
		// String jpql = "SELECT ABS(-10), MOD(3, 2), SQRT(9) FROM Order o"; // Absolute, mod (rest of division), square root (9) = 3
		// String jpql = "SELECT ABS(o.total), MOD(o.total, 2), SQRT(o.total) FROM Order o"; // Wrong: mod() has type 'INTEGER', but argument is of type 'BigDecimal'
		String jpql = "SELECT ABS(o.total), MOD(3, 2), SQRT(o.total) FROM Order o WHERE ABS(o.total) > 0.01";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(r -> System.out.println(r[0] + "  -->  " + r[1] + "  -->  " + r[2]));
	}
	
	@Test
	public void testFunctionsCollection() {
		String jpql = "SELECT SIZE(o.items) FROM Order o WHERE SIZE(o.items) >= 1";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(r -> System.out.println(r[0]));
	}
	
	@Test
	public void testNativeFunctions() {
		// FUNCTION 'dayname' is from MySQL
		// FUNCTION 'greather_than_average_billing' is mine
		String jpql = "SELECT FUNCTION('dayname', o.createdAt) FROM Order o WHERE FUNCTION('greather_than_average_billing', o.total) = 1"; // FUNCTION(name, ...params) = 1 ? true : false
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(r -> System.out.println(r[0]));
	}
}
