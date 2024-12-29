package com.algaworks.ecommerce.knownentitymanager;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderStatus;

public class FlushTests extends EntityManagerTests {
	@Test
	public void testFlush() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));

		entityManager.getTransaction().begin();
		try {
			order.setStatus(OrderStatus.PAID);
			entityManager.merge(order);
			
			// For default, update execute only in commit
			// But with flush, update execute immediately in this instruction
			// And rollback works normally
			// Is possible use flush before clear to execute command in database and clear memory from entity manager
			// Used in rare cases
			entityManager.flush();
			
			if (order.getCardPayment() == null) {
				throw new IllegalStateException("Order don't have a payment method");
			}
			
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			ex.printStackTrace();
		}
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNotEquals(OrderStatus.PAID, actualOrder.getStatus());
	}
}
