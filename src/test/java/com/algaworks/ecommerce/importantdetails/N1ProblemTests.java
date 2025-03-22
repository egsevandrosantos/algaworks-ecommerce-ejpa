package com.algaworks.ecommerce.importantdetails;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import jakarta.persistence.EntityGraph;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class N1ProblemTests extends EntityManagerTests {
    @Test
    public void testN1Problem() {
        // N+1 problem (load client from orders in another query because is EAGER)
        // List<Order> orders = entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();

        // Solve with JPQL
        // List<Order> orders = entityManager.createQuery("SELECT o FROM Order o JOIN FETCH o.client", Order.class).getResultList();

        // Solve with EntityGraph
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes(Order_.CLIENT);
        List<Order> orders = entityManager.createQuery("SELECT o FROM Order o", Order.class)
            .setHint(GraphSemantic.LOAD.getJakartaHintName(), entityGraph)
            .getResultList();

        Assertions.assertFalse(orders.isEmpty());
    }
}
