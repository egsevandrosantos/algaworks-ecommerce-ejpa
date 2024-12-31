package com.algaworks.ecommerce.advancedmapping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;

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
}
