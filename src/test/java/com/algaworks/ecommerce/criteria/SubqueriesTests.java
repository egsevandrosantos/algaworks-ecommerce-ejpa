package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
}
