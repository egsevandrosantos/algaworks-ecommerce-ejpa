package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class PathExpressionsTests extends EntityManagerTests {
    @Test
    public void testPathExpressions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
            criteriaBuilder.like(root.get(Order_.client).get(Client_.name), "J%")
        );

        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        List<Order> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
    }

    @Test
    public void testFindOrdersWithProductId() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, OrderItem> itemJoin = root.join(Order_.items);

        criteriaQuery.select(root);

        criteriaQuery.where(
            criteriaBuilder.equal(itemJoin.get(OrderItem_.product).get(Product_.id), UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"))
        );

        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        List<Order> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        System.out.println(items.size());
    }
}
