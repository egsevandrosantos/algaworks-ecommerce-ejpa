package com.algaworks.ecommerce.basicmapping;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderAddress;
import com.algaworks.ecommerce.model.OrderStatus;

public class EmbeddedMapperTests extends EntityManagerTests {
	@Test
	public void analyseEmbeddedObject() {
		OrderAddress orderAddress = new OrderAddress();
		orderAddress.setZipCode("00000-000");
		orderAddress.setAddress("Rua das Laranjeiras");
		orderAddress.setNeighborhood("Centro");
		orderAddress.setNumber("123");
		orderAddress.setCity("Uberlândia");
		orderAddress.setState("MG");
		
		Order order = new Order();
		order.setOrderedAt(Instant.now());
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(new BigDecimal("1000.00"));
		order.setAddress(orderAddress);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder));
	}
}
