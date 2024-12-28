package com.algaworks.ecommerce.relationship;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;

public class EagerAndLazyTests extends EntityManagerTests {
	@Test
	public void testEagerAndLazy() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		// By default, if is one instance, for example client, the default is load with EAGER
		// And, if is a collection, for example items, the default is load with LAZY
		// Assertions.assertTrue(Hibernate.isInitialized(order.getClient()) && !Hibernate.isInitialized(order.getItems()));
		
		// Problem N+1 where N is relationships to load and 1 is root select
		// order.getItems().size();
		// Assertions.assertTrue(Hibernate.isInitialized(order.getItems()));
		
		Assertions.assertNotNull(order);
	}
}
