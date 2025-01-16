package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderStatus;
import com.algaworks.ecommerce.model.Order_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class LogicalExpressionsTests extends EntityManagerTests {
	@Test
	public void testAndOr() {
		Instant last5Days = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusDays(5)
			.toInstant();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		/*criteriaQuery.where(
			criteriaBuilder.greaterThan(root.get(Order_.total), new BigDecimal("10.00")),
			criteriaBuilder.equal(root.get(Order_.status), OrderStatus.WAITING) // Implicit AND
		);*/
		criteriaQuery.where(
			criteriaBuilder.and( // Explicit AND
				criteriaBuilder.greaterThan(root.get(Order_.total), new BigDecimal("10.00")),
				criteriaBuilder.or(
					criteriaBuilder.equal(root.get(Order_.status), OrderStatus.WAITING),
					criteriaBuilder.equal(root.get(Order_.status), OrderStatus.PAID)
				)
			),
			criteriaBuilder.greaterThan(root.get(Order_.createdAt), last5Days) // Implicit AND
		);
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
		
		System.out.println(orders.size());
	}
}
