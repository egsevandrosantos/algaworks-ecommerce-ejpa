package com.algaworks.ecommerce.advancedmapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Attribute;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Product;

public class ElementCollectionTests extends EntityManagerTests {
	@Test
	public void testElementCollectionTagsProduct() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		entityManager.getTransaction().begin();
		product.setTags(List.of("ebook", "digital-book"));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertTrue(actualProduct.getTags() != null && !actualProduct.getTags().isEmpty());
	}
	
	@Test
	public void testElementCollectionAttributesProduct() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		entityManager.getTransaction().begin();
		product.setAttributes(List.of(new Attribute("Window", "320x600 px")));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertTrue(actualProduct.getAttributes() != null && !actualProduct.getAttributes().isEmpty());
	}
	
	@Test
	public void testElementCollectionContatcsClient() {
		Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
		
		entityManager.getTransaction().begin();
		client.setContacts(Collections.singletonMap("email", "joao.da.silva@example.com"));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Client actualClient = entityManager.find(Client.class, client.getId());
		Assertions.assertTrue(actualClient.getContacts() != null && !actualClient.getContacts().isEmpty());
	}
}
