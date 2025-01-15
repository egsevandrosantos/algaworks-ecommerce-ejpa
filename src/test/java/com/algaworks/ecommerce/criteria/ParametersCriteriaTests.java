package com.algaworks.ecommerce.criteria;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

public class ParametersCriteriaTests extends EntityManagerTests {
	@Test
	public void testFindByIdWithParams() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		// ParameterExpression<UUID> parameterId = criteriaBuilder.parameter(UUID.class);
		ParameterExpression<UUID> parameterId = criteriaBuilder.parameter(UUID.class, "id");
		
		// criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37")));
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterId));
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		// query.setParameter(parameterId, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		query.setParameter("id", UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
	}
	
	@Test
	public void testFindByDateWithParams() {
		Instant firstDate = Instant.now()
			.atOffset(ZoneOffset.UTC)
			.minusMonths(1)
			.with(LocalTime.of(0, 0, 0))
			.toInstant();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
		Root<Invoice> root = criteriaQuery.from(Invoice.class);
		
		criteriaQuery.select(root);
		
		ParameterExpression<Instant> parameterFirstDate = criteriaBuilder.parameter(Instant.class, "firstDate");
		
		criteriaQuery.where(criteriaBuilder.greaterThan(root.get("emissionDate"), parameterFirstDate));
		
		TypedQuery<Invoice> query = entityManager.createQuery(criteriaQuery);
		// query.setParameter("firstDate", new Date(), TemporalType.TIMESTAMP);
		query.setParameter("firstDate", firstDate);
		
		List<Invoice> invoices = query.getResultList();
		
		Assertions.assertFalse(invoices.isEmpty());
	}
}
