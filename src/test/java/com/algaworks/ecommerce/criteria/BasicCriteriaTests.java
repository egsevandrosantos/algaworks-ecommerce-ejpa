package com.algaworks.ecommerce.criteria;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class BasicCriteriaTests extends EntityManagerTests {
	@Test
	public void testFindById() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root); // Default (optional if use default value)
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37")));
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
	}
	
	@Test
	public void testSelectClientFromOrder() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Order> root = criteriaQuery.from(Order.class); // FROM Order
		
		criteriaQuery.select(root.get("client")); // FROM Order SELECT client
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37")));
		
		TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
		
		List<Client> clients = query.getResultList();
		
		Assertions.assertFalse(clients.isEmpty());
	}
}
