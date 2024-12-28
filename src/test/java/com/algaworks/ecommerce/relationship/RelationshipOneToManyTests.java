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
import com.algaworks.ecommerce.model.Product;

public class RelationshipOneToManyTests extends EntityManagerTests {
	@Test
	public void testRelationshipClientOrder() {
		Client client = new Client();
		client.setName("Ciclano Torres");
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setOrderedAt(Instant.now());
		order.setTotal(BigDecimal.TEN);
		
		order.setClient(client);
		client.setOrders(List.of(order));

		entityManager.getTransaction().begin();
		entityManager.persist(client);
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();

		Client actualClient = entityManager.find(Client.class, client.getId());
		Assertions.assertTrue(client.fullEquals(actualClient) && actualClient.getOrders().size() == 1 && Objects.equals(actualClient.getOrders().get(0).getId(), order.getId()));
	}
	
	@Test
	public void testRelationshipOrderOrderItem() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		Order order = new Order();
		order.setOrderedAt(Instant.now());
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(BigDecimal.TEN);
		order.setClient(client);
		
		OrderItem orderItem = new OrderItem();
		orderItem.setProductPrice(BigDecimal.TEN);
		orderItem.setQuantity(1);
		
		orderItem.setOrder(order);
		orderItem.setProduct(product);
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
