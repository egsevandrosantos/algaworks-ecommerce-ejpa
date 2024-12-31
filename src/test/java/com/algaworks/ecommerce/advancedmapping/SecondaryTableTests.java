package com.algaworks.ecommerce.advancedmapping;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.ClientSex;

public class SecondaryTableTests extends EntityManagerTests {
	@Test
	public void testSecondaryTableClient() {
		Client client = new Client();
		client.setName("Carlos Finotti");
		client.setSex(ClientSex.MALE);
		client.setBirthDate(LocalDate.of(1999, 7, 6));
		
		entityManager.getTransaction().begin();
		entityManager.persist(client);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actualClient = entityManager.find(Client.class, client.getId());
		Assertions.assertTrue(client.fullEquals(actualClient));
	}
}
