package com.algaworks.ecommerce.util;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CreateEntityManager {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		// Do your tests
		Product product = entityManager.find(Product.class, 1);
		System.out.println(product.getName());
		
		entityManager.close();
		entityManagerFactory.close();
	}
}
