package com.algaworks.ecommerce.jpql;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.model.Client;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class BasicJpqlTests extends EntityManagerTests {
	@Test
	public void testFindById() {
		// Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class);
		query.setParameter("id", UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		Order order = query.getSingleResult();
		Assertions.assertNotNull(order);
		
		// List<Order> orders = query.getResultList();
		// Assertions.assertFalse(orders.isEmpty());
	}
	
	@Test
	public void differencesTypedQueryAndQuery() {
		String jpql = "SELECT o FROM Order o WHERE o.id = :id";
		UUID id = UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37");

		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
		Query query = entityManager.createQuery(jpql);
		
		typedQuery.setParameter("id", id);
		query.setParameter("id", id);
		
		Order order = typedQuery.getSingleResult();
		Assertions.assertNotNull(order);
		
		// order = (Order) query.getSingleResult();
		// Assertions.assertNotNull(order);
		
		// List<Order> orders = typedQuery.getResultList();
		// Assertions.assertFalse(orders.isEmpty());
		
		// orders = (List<Order>) query.getResultList(); // @SuppressWarnings("unchecked")
		// Assertions.assertFalse(orders.isEmpty());
	}
	
	@Test
	public void testSelectOneFieldToResult() {
		TypedQuery<String> queryProductsName = entityManager.createQuery("SELECT p.name FROM Product p", String.class);
		List<String> productsName = queryProductsName.getResultList();
		Assertions.assertTrue(String.class.equals(productsName.get(0).getClass()));
		
		TypedQuery<Client> queryOrdersClients = entityManager.createQuery("SELECT o.client FROM Order o", Client.class);
		List<Client> ordersClients = queryOrdersClients.getResultList();
		Assertions.assertTrue(Client.class.equals(ordersClients.get(0).getClass()));
	}
	
	@Test
	public void testProjection() {
		// String jpql = "SELECT p.id, p.name FROM Product p";
		// Equals
		String jpql = "SELECT id, name FROM Product";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> result = query.getResultList();
		
		Assertions.assertTrue(result.get(0).length == 2 && UUID.class.equals(result.get(0)[0].getClass()) && String.class.equals(result.get(0)[1].getClass()));
	}
	
	@Test
	public void testProjectionWithDTO() {
		String jpql = "SELECT new ProductDTO(id, name) FROM Product";
		
		TypedQuery<ProductDTO> queryProducts = entityManager.createQuery(jpql, ProductDTO.class);
		List<ProductDTO> products = queryProducts.getResultList();
		
		Assertions.assertTrue(UUID.class.equals(products.get(0).getId().getClass()) && String.class.equals(products.get(0).getName().getClass()));
	}
	
	@Test
	public void testOrdering() {
		// String jpql = "SELECT c FROM Client c ORDER BY c.name"; // Default is ASC
		// String jpql = "SELECT c FROM Client c ORDER BY c.id"; // Default is ASC
		// String jpql = "SELECT c FROM Client c ORDER BY c.name ASC"; // Default is ASC
		String jpql = "SELECT c FROM Client c ORDER BY c.name DESC";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testDistinct() {
		// DISTINCT is for all columns
		// String jpql = "SELECT DISTINCT o.total, o.status FROM Order o JOIN o.items i";
		String jpql = "SELECT DISTINCT o.status, o.total FROM Order o JOIN o.items i";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> result = query.getResultList();
		Assertions.assertFalse(result.isEmpty());
		
		result.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
	}
}
