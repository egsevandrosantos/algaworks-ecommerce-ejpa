package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.*;
import com.algaworks.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class GroupByTests extends EntityManagerTests {
    @Test
    public void testTotalProductsByCategory() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Category> root = criteriaQuery.from(Category.class);
        Join<Category, Product> productsJoin = root.join(Category_.products, JoinType.LEFT);

        criteriaQuery.multiselect(
            root.get(Category_.name),
            criteriaBuilder.count(productsJoin.get(Product_.id))
        );

        criteriaQuery.groupBy(root.get(Category_.id));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testTotalSalesByCategory() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Product> productJoin = root.join(OrderItem_.product);
        Join<Product, Category> categoryJoin = productJoin.join(Product_.categories);

        criteriaQuery.multiselect(
            categoryJoin.get(Category_.name),
            criteriaBuilder.sum(criteriaBuilder.prod(root.get(OrderItem_.productPrice), root.get(OrderItem_.quantity)))
        );

        criteriaQuery.groupBy(categoryJoin.get(Category_.id));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }

    @Test
    public void testTotalSalesByClient() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Client> clientJoin = root.join(Order_.client);

        criteriaQuery.multiselect(
            clientJoin.get(Client_.name),
            criteriaBuilder.sum(root.get(Order_.total))
        );

        criteriaQuery.groupBy(clientJoin.get(Client_.id));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }
}
