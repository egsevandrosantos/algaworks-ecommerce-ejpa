package com.algaworks.ecommerce.criteria;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.Payment;
import com.algaworks.ecommerce.model.PaymentStatus;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class JoinCriteriaTests extends EntityManagerTests {
	@Test
	public void testJoin() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		Join<Order, Payment> paymentJoin = root.join("payment"); // INNER JOIN order.payment
		Join<Order, Client> clientJoin = root.join("client"); // INNER JOIN order.client
		Join<Order, OrderItem> orderItemJoin = root.join("items"); // INNER JOIN order.items
		Join<Order, Product> productJoin = orderItemJoin.join("product"); // INNER JOIN order.items.product
		
		criteriaQuery.multiselect(root, paymentJoin, clientJoin, productJoin);
		
		criteriaQuery.where(criteriaBuilder.equal(paymentJoin.get("status"), PaymentStatus.PROCESSING));
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> result = query.getResultList();
		
		Assertions.assertFalse(result.isEmpty());
		
		System.out.println(result.size());
	}
}
