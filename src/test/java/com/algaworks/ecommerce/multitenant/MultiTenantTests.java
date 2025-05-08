package com.algaworks.ecommerce.multitenant;

import com.algaworks.ecommerce.EntityManagerFactoryTests;
import com.algaworks.ecommerce.hibernate.EcmCurrentTenantIdentifierResolver;
import com.algaworks.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
}
