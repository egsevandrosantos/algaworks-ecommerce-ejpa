package com.algaworks.ecommerce.relationship;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderStatus;
import com.algaworks.ecommerce.model.Product;

public class RelationshipManyToOneTests extends EntityManagerTests {
	@Test
	public void testRelationshipOrderClient() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setOrderedAt(Instant.now());
		order.setTotal(BigDecimal.TEN);
		
		order.setClient(client);

		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && order.getClient().fullEquals(actualOrder.getClient()));
	}
	
	@Test
	public void testRelationshipOrderItemOrder() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		OrderItem orderItem = new OrderItem();
		orderItem.setProductPrice(BigDecimal.TEN);
		orderItem.setQuantity(1);
		
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		
		entityManager.getTransaction().begin();
		entityManager.persist(orderItem);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		OrderItem actualOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
		Assertions.assertTrue(orderItem.fullEquals(actualOrderItem) && orderItem.getOrder().fullEquals(actualOrderItem.getOrder()));
	}
	
	@Test
	public void testRelationshipOrderItemProduct() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		OrderItem orderItem = new OrderItem();
		orderItem.setProductPrice(BigDecimal.TEN);
		orderItem.setQuantity(1);
		
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		
		entityManager.getTransaction().begin();
		entityManager.persist(orderItem);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		OrderItem actualOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
		Assertions.assertTrue(orderItem.fullEquals(actualOrderItem) && orderItem.getProduct().fullEquals(actualOrderItem.getProduct()));
	}
}
