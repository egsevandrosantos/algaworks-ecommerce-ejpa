package com.algaworks.ecommerce.knownentitymanager;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderStatus;

public class TransactionsManagementTests extends EntityManagerTests {
	@Test
	public void testTransactionManagement() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		entityManager.getTransaction().begin();
		order.setStatus(OrderStatus.PAID);
		entityManager.merge(order);
		if (order.getPayment() != null) {
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().rollback();
			new IllegalStateException("Order don't have a payment method").printStackTrace();
		}
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNotEquals(OrderStatus.PAID, actualOrder.getStatus());
	}
	
	@Test
	public void testTransactionManagementWithException() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		entityManager.getTransaction().begin();
		try {
			setPaidToOrder(order);
			entityManager.merge(order);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			ex.printStackTrace();
		}
		
		entityManager.clear();
		
		Order actualOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNotEquals(OrderStatus.PAID, actualOrder.getStatus());
	}
	
	private void setPaidToOrder(Order order) {
		order.setStatus(OrderStatus.PAID);

		if (order.getPayment() == null) {
			throw new IllegalStateException("Order don't have a payment method");
		}		
	}
}
