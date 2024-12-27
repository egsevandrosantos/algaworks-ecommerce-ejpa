package com.algaworks.ecommerce.relationship;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderStatus;

public class RelationshipOneToManyTests extends EntityManagerTests {
	@Test
	public void testRelationshipClientOrder() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setOrderedAt(Instant.now());
		order.setTotal(BigDecimal.TEN);
		
		order.setClient(client);
		client.getOrders().add(order);

		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();

		Client actualClient = entityManager.find(Client.class, client.getId());
		Assertions.assertTrue(client.fullEquals(actualClient) && actualClient.getOrders().size() == 1 && Objects.equals(actualClient.getOrders().get(0).getId(), order.getId()));
	}
	
	@Test
	public void testRelationshipOrderOrderItem() {
		Order order = new Order();
		order.setOrderedAt(Instant.now());
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(BigDecimal.TEN);
		
		OrderItem orderItem = new OrderItem();
		orderItem.setProductPrice(BigDecimal.TEN);
		orderItem.setQuantity(1);
		
		orderItem.setOrder(order);
		order.setItems(List.of(orderItem));
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.persist(orderItem);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && actualOrder.getItems().size() == 1 && Objects.equals(orderItem.getId(), actualOrder.getItems().get(0).getId()));
	}
}
