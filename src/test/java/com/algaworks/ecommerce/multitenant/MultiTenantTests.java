package com.algaworks.ecommerce.multitenant;

import com.algaworks.ecommerce.EntityManagerFactoryTests;
import com.algaworks.ecommerce.hibernate.EcmCurrentTenantIdentifierResolver;
import com.algaworks.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class MultiTenantTests extends EntityManagerFactoryTests {
    @Test
    public void multitenancyBySchemaTest() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("algaworks_ecommerce");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertEquals("Kindle", product.getName());
        }

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("shop_ecommerce");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertEquals("Kindle", product.getName());
        }
    }

    @Test
    public void multitenancyByMachineTest() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("algaworks_ecommerce");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertEquals("Kindle", product.getName());
        }

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("shop_ecommerce");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertEquals("Kindle", product.getName());
        }
    }

    @Test
    public void multitenancyByDataTest() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Product> typedQuery = entityManager.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.stock WHERE p.id = :id AND p.tenant = :tenant", Product.class);
            typedQuery.setParameter("id", UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            typedQuery.setParameter("tenant", "algaworks_ecommerce");
            Stream<Product> productStream = typedQuery.getResultStream();

            Assertions.assertEquals(0, productStream.count());
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Product> typedQuery = entityManager.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.stock WHERE p.id = :id AND p.tenant = :tenant", Product.class);
            typedQuery.setParameter("id", UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            typedQuery.setParameter("tenant", "shop_ecommerce");
            Stream<Product> productStream = typedQuery.getResultStream();

            Assertions.assertTrue(productStream.allMatch(p -> Objects.equals(p.getName(), "Kindle")));
        }
    }
}
