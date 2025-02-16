package com.algaworks.ecommerce.importantdetails;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import jakarta.persistence.EntityGraph;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityGraphTests extends EntityManagerTests {
    @Test
    public void findEssentialsAttributesFromOrderWithFind() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes(Order_.CREATED_AT, Order_.STATUS, Order_.TOTAL, Order_.INVOICE);

        Map<String, Object> properties = new HashMap<>();
        // properties.put(GraphSemantic.FETCH.getJakartaHintName(), entityGraph); // Only explicit properties to load
        properties.put(GraphSemantic.LOAD.getJakartaHintName(), entityGraph); // Explicit properties to load and the EAGER attributes

        Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"), properties);
        Assertions.assertNotNull(order);
    }

    @Test
    public void findEssentialsAttributesFromOrderWithJpql() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes(Order_.CREATED_AT, Order_.STATUS, Order_.TOTAL, Order_.INVOICE);

        List<Order> orders = entityManager.createQuery("SELECT o FROM Order o", Order.class)
            .setHint(GraphSemantic.FETCH.getJakartaHintName(), entityGraph) // Works with criteria too, setHint is a method in TypedQuery
            .getResultList();
        Assertions.assertFalse(orders.isEmpty());
    }
}
