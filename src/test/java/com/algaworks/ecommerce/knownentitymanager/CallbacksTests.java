package com.algaworks.ecommerce.knownentitymanager;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderStatus;

public class CallbacksTests extends EntityManagerTests {
	@Test
	public void testPrePersistCallback() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		Order order = new Order();
		order.setClient(client);
		order.setStatus(OrderStatus.WAITING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && order.getCreatedAt() != null && order.getCreatedAt() == order.getUpdatedAt());
	}
	
	@Test
	public void testPreUpdateCallback() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		Order order = new Order();
		order.setClient(client);
		order.setStatus(OrderStatus.WAITING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		order.setStatus(OrderStatus.PAID);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && order.getCreatedAt() != null && order.getUpdatedAt() != null && order.getCreatedAt() != order.getUpdatedAt());
	}
}
