package com.algaworks.ecommerce.concurrent;

import com.algaworks.ecommerce.EntityManagerFactoryTests;
import com.algaworks.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import java.util.UUID;

public class OptimisticLockTests extends EntityManagerFactoryTests  {
    @Test
    public void useOptimisticLockTest() throws InterruptedException {
        Runnable runnable1 = () -> {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Runnable 01 loads Product Kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));

                log("Runnable 01 wait 3 seconds");
                wait(3);

                log("Runnable 01 update product");
                product.setDescription("Kindle description 01");

                log("Runnable 01 commit");
                entityManager.getTransaction().commit();
            }
        };

        Runnable runnable2 = () -> {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Runnable 02 loads Product Kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));

                log("Runnable 02 wait 1 second");
                wait(1);

                log("Runnable 02 update product");
                product.setDescription("Kindle description 02");

                log("Runnable 02 commit");
                entityManager.getTransaction().commit();
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertEquals("Kindle description 02", product.getDescription());
        }

        // The optimistic lock occurs auto with
        /*
            @Version
	        private Integer version;
        */
    }
}
