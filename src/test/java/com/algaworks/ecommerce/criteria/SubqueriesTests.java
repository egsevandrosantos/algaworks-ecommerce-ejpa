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

    @Test
    public void testSubqueryWithExists() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(root.get(Product_.name));

        Subquery<Integer> subqueryProductAlreadySold = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> rootSubqueryProductAlreadySold = subqueryProductAlreadySold.from(OrderItem.class);
        Join<OrderItem, Product> joinProductSubqueryProductAlreadySold = rootSubqueryProductAlreadySold.join(OrderItem_.product);

        subqueryProductAlreadySold.select(criteriaBuilder.literal(1));

        subqueryProductAlreadySold.where(criteriaBuilder.equal(joinProductSubqueryProductAlreadySold, root));

        criteriaQuery.where(criteriaBuilder.exists(subqueryProductAlreadySold));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testFindClientsWithMoreThen2Orders() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.multiselect(root.get(Client_.name));

        Subquery<Long> subqueryOrdersByClient = criteriaQuery.subquery(Long.class);
        Root<Order> rootSubqueryOrdersByClient = subqueryOrdersByClient.from(Order.class);
        Join<Order, Client> clientJoinSubqueryOrdersByClient = rootSubqueryOrdersByClient.join(Order_.client);
        subqueryOrdersByClient.select(criteriaBuilder.count(criteriaBuilder.literal(1)));

        subqueryOrdersByClient.where(criteriaBuilder.equal(clientJoinSubqueryOrdersByClient, root));

        criteriaQuery.where(
            criteriaBuilder.greaterThan(
                subqueryOrdersByClient,
                2L
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testFindOrdersWithProductsByCategory() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, OrderItem> joinOrderItem = root.join(Order_.items);
        criteriaQuery.multiselect(root.get(Order_.id));

        Subquery<OrderItem> subqueryProductsByCategory = criteriaQuery.subquery(OrderItem.class);
        Root<OrderItem> rootSubqueryProductsByCategory = subqueryProductsByCategory.from(OrderItem.class);
        Join<OrderItem, Product> joinProductSubquery = rootSubqueryProductsByCategory.join(OrderItem_.product);
        Join<Product, Category> joinCategorySubquery = joinProductSubquery.join(Product_.categories);
        subqueryProductsByCategory.select(rootSubqueryProductsByCategory);

        subqueryProductsByCategory.where(criteriaBuilder.equal(joinCategorySubquery.get(Category_.id), UUID.fromString("d9e5d6f8-6605-4dcd-a21a-3839407a0a1f")));

        criteriaQuery.where(joinOrderItem.in(subqueryProductsByCategory));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testFindProductsWhereSoldPriceIsNotActual() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(Product_.name));

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> rootSubquery = subquery.from(OrderItem.class);
        Join<OrderItem, Product> joinProductSubquery = rootSubquery.join(OrderItem_.product);
        subquery.select(criteriaBuilder.literal(1));

        subquery.where(
            criteriaBuilder.equal(joinProductSubquery, root),
            criteriaBuilder.notEqual(rootSubquery.get(OrderItem_.productPrice), joinProductSubquery.get(Product_.price))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testAllFindProductsAlwaysSoldWithActualPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(Product_.name));

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> rootSubquery = subquery.from(OrderItem.class);
        subquery.select(rootSubquery.get(OrderItem_.productPrice));

        subquery.where(
            criteriaBuilder.equal(rootSubquery.get(OrderItem_.product), root)
        );

        Subquery<Integer> subqueryProductAlreadySold = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> rootSubqueryProductAlreadySold = subqueryProductAlreadySold.from(OrderItem.class);
        subqueryProductAlreadySold.select(criteriaBuilder.literal(1));
        subqueryProductAlreadySold.where(criteriaBuilder.equal(rootSubqueryProductAlreadySold.get(OrderItem_.product), root));

        criteriaQuery.where(
            criteriaBuilder.exists(subqueryProductAlreadySold),
            criteriaBuilder.equal(
                root.get(Product_.price), criteriaBuilder.all(subquery)
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testAllFindProductsAlwaysSoldMoreCheap() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(Product_.name));

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> rootSubquery = subquery.from(OrderItem.class);
        subquery.select(rootSubquery.get(OrderItem_.productPrice));

        subquery.where(
            criteriaBuilder.equal(rootSubquery.get(OrderItem_.product), root)
        );

        Subquery<Integer> subqueryProductAlreadySold = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> rootSubqueryProductAlreadySold = subqueryProductAlreadySold.from(OrderItem.class);
        subqueryProductAlreadySold.select(criteriaBuilder.literal(1));
        subqueryProductAlreadySold.where(criteriaBuilder.equal(rootSubqueryProductAlreadySold.get(OrderItem_.product), root));

        criteriaQuery.where(
            // criteriaBuilder.exists(subquery), // OR the bellow option
            criteriaBuilder.exists(subqueryProductAlreadySold),
            criteriaBuilder.greaterThan(
                root.get(Product_.price), criteriaBuilder.all(subquery)
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testAnyFindProductsAlreadySoldWithActualPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(Product_.name));

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> rootSubquery = subquery.from(OrderItem.class);
        subquery.select(rootSubquery.get(OrderItem_.productPrice));

        subquery.where(
            criteriaBuilder.equal(rootSubquery.get(OrderItem_.product), root)
        );

        criteriaQuery.where(
            criteriaBuilder.equal(
                root.get(Product_.price), criteriaBuilder.any(subquery)
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testAnyFindProductsAlreadySoldWithNotActualPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(Product_.name));

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> rootSubquery = subquery.from(OrderItem.class);
        subquery.select(rootSubquery.get(OrderItem_.productPrice));

        subquery.where(
            criteriaBuilder.equal(rootSubquery.get(OrderItem_.product), root)
        );

        criteriaQuery.where(
            criteriaBuilder.notEqual(
                root.get(Product_.price), criteriaBuilder.any(subquery)
            )
        );

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testFindProductsAlwaysSoldWithSamePriceWithAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Product> joinProduct = root.join(OrderItem_.product);
        criteriaQuery.multiselect(
            joinProduct.get(Product_.name)
        ).distinct(true);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> rootSubquery = subquery.from(OrderItem.class);
        subquery.select(rootSubquery.get(OrderItem_.productPrice));

        subquery.where(criteriaBuilder.equal(joinProduct, rootSubquery.get(OrderItem_.product)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(OrderItem_.productPrice), criteriaBuilder.all(subquery)));

        /*criteriaQuery.groupBy(joinProduct);
        criteriaQuery.having(
            criteriaBuilder.equal(
                criteriaBuilder.min(root.get(OrderItem_.productPrice)),
                criteriaBuilder.max(root.get(OrderItem_.productPrice))
            )
        );*/

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }
}
