package com.algaworks.ecommerce.importantdetails;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class ConverterTests extends EntityManagerTests {
    @Test
    public void test() {
        Product product = new Product();
        product.setCreatedAt(Instant.now());
        product.setName("Powerbank Notebook Dell");
        product.setActive(Boolean.TRUE);

        entityManager.getTransaction().begin();

        entityManager.persist(product);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct = entityManager.find(Product.class, product.getId());
        Assertions.assertTrue(actualProduct.isActive());
    }
}
