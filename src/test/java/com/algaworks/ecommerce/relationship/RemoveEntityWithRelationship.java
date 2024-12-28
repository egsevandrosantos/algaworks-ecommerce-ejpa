package com.algaworks.ecommerce.relationship;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;

public class RemoveEntityWithRelationship extends EntityManagerTests {
	@Test
	public void testRemoveOrderWithItem() {
		Order order = entityManager.find(Order.class, UUID.fromString("07e419cc-f461-42c6-8055-fca267c407ef"));
		
		entityManager.getTransaction().begin();
		order.getItems().forEach(entityManager::remove);
		entityManager.remove(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, UUID.fromString("07e419cc-f461-42c6-8055-fca267c407ef"));	
		Assertions.assertNull(actualOrder);
	}
}
