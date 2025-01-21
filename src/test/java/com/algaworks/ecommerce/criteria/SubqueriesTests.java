package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.*;
import com.algaworks.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SubqueriesTests extends EntityManagerTests {
    @Test
    public void testProductsMoreExpensive() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(Product_.name), root.get(Product_.price));

        Subquery<BigDecimal> subqueryMaxPrice = criteriaQuery.subquery(BigDecimal.class);
        Root<Product> rootSubqueryMaxPrice = subqueryMaxPrice.from(Product.class);
        subqueryMaxPrice.select(criteriaBuilder.max(rootSubqueryMaxPrice.get(Product_.price)));

        criteriaQuery.where(
            criteriaBuilder.equal(
                root.get(Product_.price),
                subqueryMaxPrice
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testOrdersGreaterThanAvg() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.multiselect(root.get(Order_.id), root.get(Order_.total));

        Subquery<BigDecimal> subqueryAvgTotal = criteriaQuery.subquery(BigDecimal.class);
        Root<Order> rootSubqueryAvgTotal = subqueryAvgTotal.from(Order.class);
        subqueryAvgTotal.select(criteriaBuilder.avg(rootSubqueryAvgTotal.get(Order_.total)).as(BigDecimal.class));

        criteriaQuery.where(
            criteriaBuilder.greaterThan(
                root.get(Order_.total),
                subqueryAvgTotal
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testFindGoodClients() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.multiselect(root.get(Client_.name));

        Subquery<BigDecimal> subquerySumOrders = criteriaQuery.subquery(BigDecimal.class);
        Root<Order> rootSubquerySumOrders = subquerySumOrders.from(Order.class);
        subquerySumOrders.select(criteriaBuilder.sum(rootSubquerySumOrders.get(Order_.total)));

        subquerySumOrders.where(criteriaBuilder.equal(root, rootSubquerySumOrders.get(Order_.client)));

        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(subquerySumOrders, new BigDecimal("20.00")));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testSubqueryWithIn() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(root.get(Order_.id), root.get(Order_.total));

        Subquery<UUID> subqueryOrderItemId = criteriaQuery.subquery(UUID.class);
        Root<OrderItem> rootSubqueryOrderItemId = subqueryOrderItemId.from(OrderItem.class);
        Join<OrderItem, Order> joinOrderSubquery = rootSubqueryOrderItemId.join(OrderItem_.order);
        Join<OrderItem, Product> joinProductSubquery = rootSubqueryOrderItemId.join(OrderItem_.product);

        subqueryOrderItemId.select(joinOrderSubquery.get(Order_.id));
        subqueryOrderItemId.where(criteriaBuilder.greaterThan(joinProductSubquery.get(Product_.price), new BigDecimal("500.00")));

        criteriaQuery.where(root.get(Order_.id).in(subqueryOrderItemId));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }
}
