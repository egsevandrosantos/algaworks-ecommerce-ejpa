package com.algaworks.ecommerce.criteria;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.algaworks.ecommerce.model.*;
import jakarta.persistence.criteria.Join;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class FunctionsTests extends EntityManagerTests {
	@Test
	public void testStringFunctions() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Client> root = criteriaQuery.from(Client.class);
		
		criteriaQuery.multiselect(
			root.get(Client_.name),
			criteriaBuilder.concat("Nome do cliente: ", root.get(Client_.name)),
			criteriaBuilder.length(root.get(Client_.name)),
			criteriaBuilder.locate(root.get(Client_.name), "a"),
			criteriaBuilder.substring(root.get(Client_.name), 1, 2),
			criteriaBuilder.lower(root.get(Client_.name)),
			criteriaBuilder.upper(root.get(Client_.name)),
			criteriaBuilder.trim(root.get(Client_.name))
		);
		
		criteriaQuery.where(
			criteriaBuilder.greaterThan(
				criteriaBuilder.length(root.get(Client_.name)), 1
			)
		);
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> items = query.getResultList();
		Assertions.assertFalse(items.isEmpty());
		items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
	
	@Test
	public void testDateFunctions() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		Join<Order, Payment> paymentJoin = root.join(Order_.payment);
		Join<Order, BankSlipPayment> bankSlipPaymentJoin = criteriaBuilder.treat(paymentJoin, BankSlipPayment.class);
		
		criteriaQuery.multiselect(
			root.get(Order_.id),
			criteriaBuilder.currentDate(),
			criteriaBuilder.currentTime(),
			criteriaBuilder.currentTimestamp(), // With my timezone
			criteriaBuilder.localDate(),
			criteriaBuilder.localTime(),
			criteriaBuilder.localDateTime()
		);

		criteriaQuery.where(
			criteriaBuilder.between(
				criteriaBuilder.currentTimestamp().as(Instant.class),
				root.get(Order_.createdAt).as(Instant.class),
				bankSlipPaymentJoin.get(BankSlipPayment_.dueDate).as(Instant.class)
			)
		);
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> items = query.getResultList();
		Assertions.assertFalse(items.isEmpty());
		items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
