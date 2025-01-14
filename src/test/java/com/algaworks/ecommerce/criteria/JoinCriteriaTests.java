package com.algaworks.ecommerce.criteria;

import java.util.List;
import java.util.UUID;

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
import jakarta.persistence.criteria.JoinType;
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
	
	@Test
	public void testJoinWithOn() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		Join<Order, Payment> paymentJoin = root.join("payment"); // INNER JOIN order.payment
		paymentJoin.on(criteriaBuilder.equal(paymentJoin.get("status"), PaymentStatus.PROCESSING)); // ON status = PaymentStatus.PROCESSING
		
		criteriaQuery.multiselect(root);
		
		criteriaQuery.where(criteriaBuilder.equal(paymentJoin.get("status"), PaymentStatus.PROCESSING));
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> result = query.getResultList();
		
		Assertions.assertFalse(result.isEmpty());
		
		System.out.println(result.size());
	}
	
	@Test
	public void testLeftJoin() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		Join<Order, Payment> paymentJoin = root.join("payment", JoinType.LEFT); // LEFT JOIN order.payment
		
		criteriaQuery.multiselect(root);
		
		criteriaQuery.where(
			criteriaBuilder.or(
				criteriaBuilder.isNull(paymentJoin),
				criteriaBuilder.equal(paymentJoin.get("status"), PaymentStatus.PROCESSING)
			)
		);
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> result = query.getResultList();
		
		Assertions.assertFalse(result.isEmpty());
		
		System.out.println(result.size());
	}
	
	@Test
	public void testJoinFetch() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		root.fetch("items") // INNER JOIN FETCH order.items
			.fetch("product"); // INNER JOIN FETCH order.items.product
		root.fetch("client"); // INNER JOIN FETCH order.client
		// @SuppressWarnings("unchecked") Join<Order, Client> clientJoin = (Join<Order, Client>) root.<Order, Client>fetch("client");
		root.fetch("invoice", JoinType.LEFT); // LEFT JOIN FETCH order.invoice
		
		// criteriaQuery.select(clientJoin);
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37")));
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		Order order = query.getSingleResult();
		
		Assertions.assertFalse(order.getItems().isEmpty());
		
		System.out.println(order.getItems().size());
	}
}
