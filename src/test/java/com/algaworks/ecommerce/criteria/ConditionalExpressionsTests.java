package com.algaworks.ecommerce.criteria;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Client_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ConditionalExpressionsTests extends EntityManagerTests {
	@Test
	public void testLike() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> root = criteriaQuery.from(Client.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.like(root.get(Client_.name), "%A%"));
		
		TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
		
		List<Client> clients = query.getResultList();
		
		Assertions.assertFalse(clients.isEmpty());
	}
}
