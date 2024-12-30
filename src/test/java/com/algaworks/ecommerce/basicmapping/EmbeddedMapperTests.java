package com.algaworks.ecommerce.basicmapping;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderAddress;
import com.algaworks.ecommerce.model.OrderStatus;

public class EmbeddedMapperTests extends EntityManagerTests {
	@Test
	public void analyseEmbeddedObject() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));

		OrderAddress orderAddress = new OrderAddress();
		orderAddress.setZipCode("00000-000");
		orderAddress.setAddress("Rua das Laranjeiras");
		orderAddress.setNeighborhood("Centro");
		orderAddress.setNumber("123");
		orderAddress.setCity("Uberlândia");
		orderAddress.setState("MG");
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(new BigDecimal("1000.00"));
		order.setAddress(orderAddress);
		order.setClient(client);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder));
	}
}
