package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ConditionalExpressionsTests extends EntityManagerTests {
	@Test
	public void testLike() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> root = criteriaQuery.from(Client.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.like(root.get(Client_.name), "%A%"));
		
		TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
		
		List<Client> clients = query.getResultList();
		
		Assertions.assertFalse(clients.isEmpty());
	}
	
	@Test
	public void testIsNull() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		// criteriaQuery.where(root.get(Product_.photo).isNull());
		// criteriaQuery.where(root.get(Product_.photo).isNotNull());
		
		criteriaQuery.where(criteriaBuilder.isNull(root.get(Product_.photo)));
		// criteriaQuery.where(criteriaBuilder.isNotNull(root.get(Product_.photo)));
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
	}
	
	@Test
	public void testIsEmpty() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Product_.categories)));
		// criteriaQuery.where(criteriaBuilder.isNotEmpty(root.get(Product_.categories)));
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
	}
	
	@Test
	public void testGreaterThanEqualLessThan() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		// criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Product_.price), new BigDecimal("499.00")));
		// criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.price), new BigDecimal("499.00")));
		// criteriaQuery.where(criteriaBuilder.lessThan(root.get(Product_.price), new BigDecimal("499.00")));
		// criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(root.get(Product_.price), new BigDecimal("499.00")));
		criteriaQuery.where(
			criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.price), new BigDecimal("499.00")),
			/* AND */ criteriaBuilder.lessThanOrEqualTo(root.get(Product_.price), new BigDecimal("1400.00"))
		);
		// criteriaQuery.where(criteriaBuilder.between(root.get(Product_.price), new BigDecimal("499.00"), new BigDecimal("1400.00")));
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
		
		System.out.println(products.size());
	}
	
	@Test
	public void testFindOrdersLast3Days() {
		Instant last3Days = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusDays(3)
			// .with(LocalTime.of(0, 0, 0))
			.toInstant();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.createdAt), last3Days));
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
		
		System.out.println(orders.size());
	}
	
	@Test
	public void testBetween() {
		Instant now = Instant.now();
		Instant last5Days = now
			.atOffset(ZoneOffset.UTC)
			.minusDays(5)
			.toInstant();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(
			criteriaBuilder.between(root.get(Order_.total), new BigDecimal("20.00"), new BigDecimal("30.00")),
			criteriaBuilder.between(root.get(Order_.createdAt), last5Days, now)
		);
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
		
		System.out.println(orders.size());
	}
	
	@Test
	public void testDifferent() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Order_.total), new BigDecimal("10.00")));
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
		
		System.out.println(orders.size());
	}

	@Test
	public void testCase() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);

		criteriaQuery.multiselect(
			root.get(Order_.id),
			root.get(Order_.status),
			criteriaBuilder.selectCase(root.get(Order_.status))
				.when(OrderStatus.WAITING, "Wait a minute")
				.when(OrderStatus.PAID, "Already paid")
				.when(OrderStatus.CANCELLED, "Already cancelled")
				.otherwise("Invalid"),
			root.get(Order_.payment).type(),
			criteriaBuilder.selectCase(root.get(Order_.payment).type())
				.when(BankSlipPayment.class, "Bank slip payment")
				.when(CardPayment.class, "Card payment")
				.otherwise("Invalid")
		);

		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> items = query.getResultList();
		Assertions.assertFalse(items.isEmpty());
		items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
