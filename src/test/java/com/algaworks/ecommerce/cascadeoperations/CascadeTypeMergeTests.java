package com.algaworks.ecommerce.cascadeoperations;

import java.util.List;
import java.util.Objects;
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

public class CascadeTypeMergeTests extends EntityManagerTests {
	@Test
	public void testMergeOrderWithOrderItems() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		Order order = new Order();
		order.setId(UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		order.setClient(client);
		order.setTotal(product.getPrice());
		order.setStatus(OrderStatus.WAITING);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemId());
		item.getId().setOrderId(order.getId());
		item.getId().setProductId(product.getId());
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(3);
		item.setProductPrice(product.getPrice());
		
		order.setItems(List.of(item)); // Update with CascadeType.MERGE
		
		entityManager.getTransaction().begin();
		entityManager.merge(order);
		// entityManager.merge(item); // Will be updated with CascadeType.MERGE
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.equals(actualOrder) && actualOrder.getItems().size() == 1 && item.fullEquals(actualOrder.getItems().get(0)));
	}
	
	@Test
	public void testMergeOrderItemsWithOrder() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));

		Order order = new Order();
		order.setId(UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		order.setClient(client);
		order.setTotal(product.getPrice());
		order.setStatus(OrderStatus.PAID);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemId());
		item.getId().setOrderId(order.getId());
		item.getId().setProductId(product.getId());
		item.setOrder(order); // Update with CascadeType.MERGE
		item.setProduct(product);
		item.setQuantity(5);
		item.setProductPrice(product.getPrice());
		
		order.setItems(List.of(item));
		
		entityManager.getTransaction().begin();
		// entityManager.merge(order); // Will be updated with CascadeType.MERGE
		entityManager.merge(item);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(
			order.equals(actualOrder)
				&& Objects.equals(order.getStatus(), actualOrder.getStatus())
		);
	}
}
