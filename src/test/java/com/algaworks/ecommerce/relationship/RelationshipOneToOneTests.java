package com.algaworks.ecommerce.relationship;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.CardPayment;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.PaymentStatus;

public class RelationshipOneToOneTests extends EntityManagerTests {
	@Test
	public void testCardPaymentOrderRelationship() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		CardPayment cardPayment = new CardPayment();
		cardPayment.setCardNumber("1234");
		cardPayment.setStatus(PaymentStatus.PROCESSING);

		cardPayment.setOrder(order);

		entityManager.getTransaction().begin();
		entityManager.persist(cardPayment);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertTrue(actualOrder.getCardPayment().fullEquals(cardPayment));
	}
}
