package com.algaworks.ecommerce.advancedmapping;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;

public class TransientPropertyTests extends EntityManagerTests {
	@Test
	public void testTransient() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		Assertions.assertEquals("João", client.getFirstName());
	}
}
