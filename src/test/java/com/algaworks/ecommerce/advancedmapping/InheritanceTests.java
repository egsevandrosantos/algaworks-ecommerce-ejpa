package com.algaworks.ecommerce.advancedmapping;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.CardPayment;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Payment;
import com.algaworks.ecommerce.model.PaymentStatus;

public class InheritanceTests extends EntityManagerTests {
	@Test
	public void testInheritance() {
		Client client = new Client();
		client.setName("Fernanda Morais");
		
		entityManager.getTransaction().begin();
		entityManager.persist(client);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actualClient = entityManager.find(Client.class, client.getId());
		Assertions.assertTrue(client.fullEquals(actualClient));
	}
	
	@Test
	public void testListPayments() {
		@SuppressWarnings("unchecked")
		List<Payment> payments = entityManager.createQuery("SELECT p FROM Payment p").getResultList();
		
		Assertions.assertFalse(payments.isEmpty());
	}
	
	@Test
	public void testInsertOrderPayment() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		CardPayment cardPayment = new CardPayment();
		cardPayment.setOrder(order);
		cardPayment.setStatus(PaymentStatus.PROCESSING);
		cardPayment.setCardNumber("1234 5678 9012 3456");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cardPayment);
		entityManager.getTransaction().commit();
		
		CardPayment actualCardPayment = entityManager.find(CardPayment.class, cardPayment.getId());
		Assertions.assertTrue(cardPayment.fullEquals(actualCardPayment));
	}
}
