package com.algaworks.ecommerce.criteria;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Client_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

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
	
	@Test
	public void testIsNull() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		// criteriaQuery.where(root.get(Product_.photo).isNull());
		// criteriaQuery.where(root.get(Product_.photo).isNotNull());
		
		criteriaQuery.where(criteriaBuilder.isNull(root.get(Product_.photo)));
		// criteriaQuery.where(criteriaBuilder.isNotNull(root.get(Product_.photo)));
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
	}
	
	@Test
	public void testIsEmpty() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Product_.categories)));
		// criteriaQuery.where(criteriaBuilder.isNotEmpty(root.get(Product_.categories)));
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
	}
}
