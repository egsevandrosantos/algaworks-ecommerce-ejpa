package com.algaworks.ecommerce.jpql;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.OrderStatus;

import jakarta.persistence.TypedQuery;

public class ConditionalExpressionsTests extends EntityManagerTests {
	@Test
	public void testLikeOperator() {
		// String jpql = "SELECT c FROM Client c WHERE c.name LIKE :name";
		// String jpql = "SELECT c FROM Client c WHERE c.name LIKE CONCAT(:name, '%')";
		// String jpql = "SELECT c FROM Client c WHERE c.name LIKE CONCAT('%', :name)";
		String jpql = "SELECT c FROM Client c WHERE c.name LIKE CONCAT('%', :name, '%')";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("name", "João%");
		// query.setParameter("name", "João");
		// query.setParameter("name", "Silva");
		query.setParameter("name", "o");
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testIsNullAndIsNotNull() {
		String jpql = "SELECT p FROM Product p WHERE p.photo IS NULL";
		// String jpql = "SELECT p FROM Product p WHERE p.photo IS NOT NULL";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testIsEmptyAndIsNotEmpty() {
		// Is empty is for collection
		String jpql = "SELECT p FROM Product p WHERE p.categories IS EMPTY"; // WHERE NOT EXISTS
		// String jpql = "SELECT p FROM Product p WHERE p.categories IS NOT EMPTY"; // WHERE EXISTS
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGreaterThanEqualAndLessThanEqual() {
		// String jpql = "SELECT p FROM Product p WHERE p.price > :price";
		// String jpql = "SELECT p FROM Product p WHERE p.price >= :price";
		// String jpql = "SELECT p FROM Product p WHERE p.price < :price";
		// String jpql = "SELECT p FROM Product p WHERE p.price <= :price";
		String jpql = "SELECT p FROM Product p WHERE p.price >= :priceInterval1 AND p.price <= :priceInterval2";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("price", new BigDecimal("499.00"));
		query.setParameter("priceInterval1", new BigDecimal("400.00"));
		query.setParameter("priceInterval2", new BigDecimal("1500.00"));
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testFindOrders2DaysAgo() {
		Instant dateInterval1 = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusDays(2)
			.with(LocalTime.of(0, 0, 0))
			.toInstant();
		Instant dateInterval2 = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.with(LocalTime.of(23, 59, 59))
			.toInstant();
		
		String jpql = "SELECT o FROM Order o WHERE o.createdAt >= :dateInterval1 AND o.createdAt <= :dateInterval2";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("dateInterval1", dateInterval1);
		query.setParameter("dateInterval2", dateInterval2);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testBetween() {
		// String jpql = "SELECT p FROM Product p WHERE p.price >= :priceInterval1 AND p.price <= :priceInterval2";
		// Equals:
		String jpql = "SELECT p FROM Product p WHERE p.price BETWEEN :priceInterval1 AND :priceInterval2";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("priceInterval1", new BigDecimal("400.00"));
		query.setParameter("priceInterval2", new BigDecimal("1500.00"));
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testFindOrders2DaysAgoWithBetween() {
		Instant dateInterval1 = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusDays(2)
			.with(LocalTime.of(0, 0, 0))
			.toInstant();
		Instant dateInterval2 = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.with(LocalTime.of(23, 59, 59))
			.toInstant();
		
		// String jpql = "SELECT o FROM Order o WHERE o.createdAt >= :dateInterval1 AND o.createdAt <= :dateInterval2";
		// Equals:
		String jpql = "SELECT o FROM Order o WHERE o.createdAt BETWEEN :dateInterval1 AND :dateInterval2";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("dateInterval1", dateInterval1);
		query.setParameter("dateInterval2", dateInterval2);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testDifferentOperator() {
		String jpql = "SELECT p FROM Product p WHERE p.price <> 100";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testCase() {
		String jpql = """
			SELECT
				o.id,
				CASE o.status
					WHEN 'PAID' THEN 'Already paid'
					WHEN 'CANCELLED' THEN 'Already cancelled'
					WHEN :waitingStatus THEN 'Please, wait a minute...'
					ELSE 'Undefined'
				END,
				CASE TYPE(o.payment)
					WHEN BankSlipPayment THEN 'Bank slip'
					WHEN CardPayment THEN 'Card'
					ELSE 'Undefined'
				END
			FROM Order o
		""";
		// BankSlipPayment and CardPayment in jpql is class, not string
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("waitingStatus", OrderStatus.WAITING);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
	
	@Test
	public void testIn() {
		// Client client1 = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		// Client client2 = entityManager.find(Client.class, UUID.fromString("00492c10-234a-4388-9375-2da767ce0d6a"));
		Client client1 = new Client();
		client1.setId(UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Client client2 = new Client();
		client2.setId(UUID.fromString("00492c10-234a-4388-9375-2da767ce0d6a"));
		
		// String jpql = "SELECT o.id, o.total, o.status FROM Order o WHERE o.id IN (:ids)";
		String jpql = "SELECT o.id, o.total, o.status FROM Order o WHERE o.client IN (:clients)";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter("ids", List.of(UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"), UUID.fromString("07e419cc-f461-42c6-8055-fca267c407ef")));
		query.setParameter("clients", List.of(client1, client2));
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
