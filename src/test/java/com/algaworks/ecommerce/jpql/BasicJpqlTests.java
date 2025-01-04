package com.algaworks.ecommerce.jpql;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

public class BasicJpqlTests extends EntityManagerTests {
	@Test
	public void testFindById() {
		// Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class);
		query.setParameter("id", UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		Order order = query.getSingleResult();
		Assertions.assertNotNull(order);
		
		// List<Order> orders = query.getResultList();
		// Assertions.assertFalse(orders.isEmpty());
	}
}
