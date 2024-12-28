package com.algaworks.ecommerce.relationship;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;

public class OptionalRelationshipTests extends EntityManagerTests {
	@Test
	public void testOptionalRelationship() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		Assertions.assertNotNull(order);
	}
}
