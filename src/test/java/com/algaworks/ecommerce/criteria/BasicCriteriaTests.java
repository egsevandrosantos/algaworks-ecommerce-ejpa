package com.algaworks.ecommerce.criteria;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.algaworks.ecommerce.model.*;
import jakarta.persistence.criteria.Join;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.dto.ProductDTO;

import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class BasicCriteriaTests extends EntityManagerTests {
	@Test
	public void testFindById() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root); // Default (optional if use default value)
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37")));
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		
		List<Order> orders = query.getResultList();
		
		Assertions.assertFalse(orders.isEmpty());
	}
	
	@Test
	public void testSelectClientFromOrder() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Order> root = criteriaQuery.from(Order.class); // FROM Order
		
		criteriaQuery.select(root.get("client")); // FROM Order SELECT client
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37")));
		
		TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
		
		List<Client> clients = query.getResultList();
		
		Assertions.assertFalse(clients.isEmpty());
	}
	
	@Test
	public void testFindAllProducts() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		List<Product> products = query.getResultList();
		
		Assertions.assertFalse(products.isEmpty());
		
		products.stream().map(p -> String.join("  -->  ", List.of(p.getId().toString(), p.getName()))).forEach(System.out::println);
	}
	
	@Test
	public void testProjections() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		// criteriaQuery.select(root);
		criteriaQuery.multiselect(root.get("id"), root.get("name"));
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> result = query.getResultList();
		
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
	
	@Test
	public void testTuple() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(
			criteriaBuilder.tuple(
				root.get("id").alias("id"),
				root.get("name").alias("name")
			)
		);
		
		TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
		
		List<Tuple> result = query.getResultList();
		
		Assertions.assertFalse(result.isEmpty());
		
		// result.forEach(tuple -> System.out.println(String.join("  -->  ", IntStream.range(0, tuple.getElements().size()).mapToObj(i -> tuple.get(i).toString()).toList())));
		for (Tuple tuple : result) {
			System.out.println(tuple.get(0) + "  -->  " + tuple.get(1));
			System.out.println(tuple.get("id") + "  -->  " + tuple.get("name"));
		}
	}
	
	@Test
	public void testProjectionToDTO() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(criteriaBuilder.construct(ProductDTO.class, root.get("id"), root.get("name")));
		
		TypedQuery<ProductDTO> query = entityManager.createQuery(criteriaQuery);
		
		List<ProductDTO> result = query.getResultList();
		
		Assertions.assertFalse(result.isEmpty());
		
		for (ProductDTO dto : result) {
			System.out.println(dto.getId() + "  -->  " + dto.getName());
		}
	}
	
	@Test
	public void testOrderBy() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> root = criteriaQuery.from(Client.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Client_.name)), criteriaBuilder.asc(root.get(Client_.birthDate)));
		// criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Client_.name)));
		
		TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
		List<Client> clients = query.getResultList();
		Assertions.assertFalse(clients.isEmpty());
		for (Client client : clients) {
			System.out.println(client.getId() + "  -->  " + client.getName());
		}
	}

	@Test
	public void testDistinct() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		root.join(Order_.items);

		criteriaQuery
			.multiselect(root.get(Order_.id))
			.distinct(true);

		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> items = query.getResultList();
		Assertions.assertFalse(items.isEmpty());
		items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
