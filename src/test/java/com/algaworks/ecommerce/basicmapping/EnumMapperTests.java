package com.algaworks.ecommerce.basicmapping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.ClientSex;

public class EnumMapperTests extends EntityManagerTests {
	@Test
	public void testEnum() {
		Client client = new Client();
		client.setName("José Mineiro");
		client.setSex(ClientSex.MALE);
		
		entityManager.getTransaction().begin();
		entityManager.persist(client);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actualClient = entityManager.find(Client.class, client.getId());
		Assertions.assertTrue(client.fullEquals(actualClient));
	}
}
