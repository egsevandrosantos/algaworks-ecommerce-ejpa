package com.algaworks.ecommerce.advancedmapping;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.CardPayment;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemId;
import com.algaworks.ecommerce.model.OrderStatus;
import com.algaworks.ecommerce.model.PaymentStatus;
import com.algaworks.ecommerce.model.Product;

public class MapsIdTests extends EntityManagerTests {
	@Test
	public void testMapsId() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		Invoice invoice = new Invoice();
		// invoice.setId(order.getId()); // Unnecessary with @MapsId
		invoice.setOrder(order);
		invoice.setEmissionDate(Instant.now());
		invoice.setXml(loadInvoiceExample());
		
		entityManager.getTransaction().begin();
		entityManager.persist(invoice);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Invoice actualInvoice = entityManager.find(Invoice.class, invoice.getId());
		Assertions.assertTrue(invoice.fullEquals(actualInvoice));
	}
	
	@Test
	public void testMapsIdWithEmbeddedId() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		Product product = entityManager.find(Product.class, UUID.fromString("4e3a1229-050e-4049-9a85-54885aa5e875"));
		
		entityManager.getTransaction().begin();

		Order order = new Order();
		order.setClient(client);
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(product.getPrice());
		entityManager.persist(order);

		OrderItem orderItem = new OrderItem();
		orderItem.setId(new OrderItemId()); // Id is mapped with @MapsId, set an empty instance to fix NullPointerException in setter
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
	
	@Test
	public void testMapsIdCardPayment() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		CardPayment cardPayment = new CardPayment();
		cardPayment.setOrder(order);
		cardPayment.setStatus(PaymentStatus.PROCESSING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cardPayment);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		CardPayment actualCardPayment = entityManager.find(CardPayment.class, cardPayment.getId());
		Assertions.assertTrue(cardPayment.fullEquals(actualCardPayment));
	}
	
	private byte[] loadInvoiceExample() {
		try {
			return MapsIdTests.class.getResourceAsStream("/invoice.xml").readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
