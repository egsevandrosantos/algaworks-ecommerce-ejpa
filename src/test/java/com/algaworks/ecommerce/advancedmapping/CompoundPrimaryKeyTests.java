package com.algaworks.ecommerce.advancedmapping;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemId;
import com.algaworks.ecommerce.model.OrderStatus;
import com.algaworks.ecommerce.model.Product;

public class CompoundPrimaryKeyTests extends EntityManagerTests {
	@Test
	public void testCompoundPrimaryKeyOrderItem() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("4e3a1229-050e-4049-9a85-54885aa5e875"));
		
		entityManager.getTransaction().begin();

		Order order = new Order();
		order.setClient(client);
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(product.getPrice());
		entityManager.persist(order);

		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(order.getId());
		orderItem.setProductId(product.getId());
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setProductPrice(product.getPrice());
		orderItem.setQuantity(1);
		entityManager.persist(orderItem);

		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		OrderItemId orderItemId = new OrderItemId(order.getId(), product.getId());
		OrderItem actualOrderItem = entityManager.find(OrderItem.class, orderItemId);
		Assertions.assertTrue(orderItem.fullEquals(actualOrderItem));
	}
}
