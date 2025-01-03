package com.algaworks.ecommerce.cascadeoperations;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemId;

public class CascadeTypeRemoveTests extends EntityManagerTests {
	@Test
	public void testRemoveOrderWithOrderItems() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		entityManager.getTransaction().begin();
		// order.getItems().forEach(entityManager::remove); // Will be removed with CascadeType.REMOVE
		entityManager.remove(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNull(actualOrder);
	}
	
	@Test
	public void testRemoveOrderItemAndOrder() {
		OrderItemId orderItemId = new OrderItemId(UUID.fromString("9e1b1c20-6b20-4d96-a6d3-1ae0c0c7dd47"), UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		OrderItem orderItem = entityManager.find(OrderItem.class, orderItemId);
		
		entityManager.getTransaction().begin();
		entityManager.remove(orderItem);
		// entityManager.remove(orderItem.getOrder()); // Will be removed with CascadeType.REMOVE
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, orderItem.getId().getOrderId());
		Assertions.assertNull(actualOrder);
	}
}
