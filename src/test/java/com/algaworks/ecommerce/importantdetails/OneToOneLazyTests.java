package com.algaworks.ecommerce.importantdetails;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OneToOneLazyTests extends EntityManagerTests {
    @Test
    public void testOneToOne() {
        System.out.println("Find one order 1");
        Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
        Assertions.assertNotNull(order);

        System.out.println("------------------------------------------------------------------------------------------");

        System.out.println("Find one order 2");
        TypedQuery<Order> query = entityManager.createQuery(
            """
                SELECT o FROM Order o
                LEFT JOIN FETCH o.payment
                LEFT JOIN FETCH o.client
                LEFT JOIN FETCH o.invoice
                WHERE o.id = :id
            """,
            Order.class
        );
        query.setParameter("id", UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
        order = query.getSingleResult();
        Assertions.assertNotNull(order);

        System.out.println("------------------------------------------------------------------------------------------");

        System.out.println("Find orders");
        query = entityManager.createQuery(
            """
                SELECT o FROM Order o
                LEFT JOIN FETCH o.payment
                LEFT JOIN FETCH o.client
                LEFT JOIN FETCH o.invoice
            """,
            Order.class
        );
        List<Order> list = query.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void testSaveOrder() {
        Client client = entityManager.find(Client.class, UUID.fromString("737fac65-ec05-4173-a522-00833a22271b"));
        Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));

        Order order = new Order();
        order.setTotal(new BigDecimal("100.00"));
        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);

        OrderItem item = new OrderItem();
        item.setId(new OrderItemId());
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(1);
        item.setProductPrice(product.getPrice());
        order.setItems(List.of(item));

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
    }
}
