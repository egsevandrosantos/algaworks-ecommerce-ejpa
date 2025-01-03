package com.algaworks.ecommerce.cascadeoperations;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.ClientSex;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemId;
import com.algaworks.ecommerce.model.OrderStatus;
import com.algaworks.ecommerce.model.Product;

public class CasdadeTypePersistTests extends EntityManagerTests {
	@Test
	public void testPersistOrderWithOrderItems() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		Order order = new Order();
		order.setClient(client);
		order.setTotal(product.getPrice());
		order.setStatus(OrderStatus.WAITING);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemId());
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(1);
		item.setProductPrice(product.getPrice());
		
		order.setItems(List.of(item)); // Save with CascadeType.PERSIST
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		// entityManager.persist(item); // Will be persisted with CascadeType.PERSIST
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && actualOrder.getItems().size() == 1 && item.fullEquals(actualOrder.getItems().get(0)));
	}
	
	@Test
	public void testPersistOrderItemWithOrder() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		Order order = new Order();
		order.setClient(client);
		order.setTotal(product.getPrice());
		order.setStatus(OrderStatus.WAITING);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemId());
		item.setOrder(order); // Save auto because is part of PK (@MapsId)
		item.setProduct(product);
		item.setQuantity(1);
		item.setProductPrice(product.getPrice());
		
		entityManager.getTransaction().begin();
		// entityManager.persist(order); // Will be persisted auto because is part of PK (@MapsId)
		entityManager.persist(item);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && actualOrder.getItems().size() == 1 && item.fullEquals(actualOrder.getItems().get(0)));
	}
	
	@Test
	public void testPersistOrderWithClient() {
		Client client = new Client();
		client.setBirthDate(LocalDate.of(1980, 1, 1));
		client.setSex(ClientSex.MALE);
		client.setName("José Carlos");
		client.setCpf("810.349.728-20");
		
		Order order = new Order();
		order.setClient(client); // Save with CascadeType.PERSIST
		order.setTotal(BigDecimal.ZERO);
		order.setStatus(OrderStatus.WAITING);
		
		entityManager.getTransaction().begin();
		// entityManager.persist(client); // Will be persisted with CascadeType.PERSIST
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(order.fullEquals(actualOrder) && actualOrder.getClient() != null && client.fullEquals(actualOrder.getClient()));
	}
	
	@Test
	public void testPersistProductWithCategory() {
		Category category = new Category();
		category.setName("Cascade");
	
		Product product = new Product();
		product.setName("Cascade");
		product.setCreatedAt(Instant.now());
		product.setCategories(List.of(category)); // Save with CascadeType.PERSIST
		
		entityManager.getTransaction().begin();
		// entityManager.persist(category); // Will be persisted with CascadeType.PERSIST
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertTrue(product.fullEquals(actualProduct) && actualProduct.getCategories().size() == 1 && category.fullEquals(actualProduct.getCategories().get(0)));
	}
}
