package com.algaworks.ecommerce.jpql;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.PaymentStatus;

import jakarta.persistence.TypedQuery;

public class ParametersJpqlTests extends EntityManagerTests {
	@Test
	public void testParametersJpql() {
		// String jpql = "SELECT o FROM Order o JOIN o.payment pay WHERE o.id = ?1 AND pay.status = ?2"; // ?1 = Position to parameter
		// String jpql = "SELECT o FROM Order o JOIN o.payment pay WHERE o.id = :orderId AND pay.status = :paymentStatus";
		String jpql = "SELECT o FROM Order o JOIN o.payment pay WHERE o.id = :orderId AND pay.status = ?1";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter(1, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		query.setParameter("orderId", UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		query.setParameter(1, PaymentStatus.PROCESSING);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testParameterDateJpql() {
		String jpql = "SELECT i FROM Invoice i WHERE i.emissionDate <= ?1";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		// query.setParameter(1, new Date(), TemporalType.TIMESTAMP);
		query.setParameter(1, Instant.now());
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
}
