package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client_;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}
