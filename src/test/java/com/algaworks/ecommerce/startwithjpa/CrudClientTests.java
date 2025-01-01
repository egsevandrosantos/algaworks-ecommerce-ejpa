package com.algaworks.ecommerce.startwithjpa;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;

public class CrudClientTests extends EntityManagerTests {
	@Test
	public void create() {
		Client expected = new Client();
		expected.setName("Maria das Flores");
		expected.setCpf("718.611.817-87");
		
		entityManager.getTransaction().begin();
		entityManager.persist(expected);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actual = entityManager.find(Client.class, expected.getId());
		Assertions.assertTrue(expected.fullEquals(actual));
	}
	
	@Test
	public void read() {
		Client expected = new Client();
		expected.setId(UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		Client actual = entityManager.find(Client.class, expected.getId());
		Assertions.assertTrue(expected.equals(actual));
	}
	
	@Test
	public void update() {
		Client expected = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		entityManager.getTransaction().begin();
		expected.setName("Jose dos Santos");
		expected = entityManager.merge(expected);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actual = entityManager.find(Client.class, expected.getId());
		Assertions.assertTrue(expected.fullEquals(actual));
	}
	
	@Test
	public void delete() {
		Client expected = entityManager.find(Client.class, UUID.fromString("00492c10-234a-4388-9375-2da767ce0d6a"));
		
		entityManager.getTransaction().begin();
		entityManager.remove(expected);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actual = entityManager.find(Client.class, expected.getId());
		Assertions.assertNull(actual);
	}
}
