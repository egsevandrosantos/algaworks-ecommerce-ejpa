package com.algaworks.ecommerce.concurrent;

import com.algaworks.ecommerce.EntityManagerFactoryTests;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class PessimisticLockTests extends EntityManagerFactoryTests {
    @Test
    public void usePessimisticReadLockTest() throws InterruptedException {
        Runnable run1 = () -> {
            log("Start run 1");

            String newDescription = "Detail description 1. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 1 get product kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"), LockModeType.PESSIMISTIC_READ);
                // Block exclusive (X) lock (for update - MySQL test)
                // If use for update, the another thread wait this, if use for update nowait, the exception is thrown
                // But is possible update in another thread without lock (MySQL test)

                log("Run 1 change description");
                product.setDescription(newDescription);

                log("Run 1 wait 3 seconds");
                wait(3);

                log("Run 1 commit");
                entityManager.getTransaction().commit();

                log("Run 1 end");
            }
        };

        Runnable run2 = () -> {
            log("Start run 2");

            String newDescription = "Detail description 2. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 2 get product kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"), LockModeType.PESSIMISTIC_READ);

                log("Run 2 change description");
                product.setDescription(newDescription);

                log("Run 2 commit");
                entityManager.getTransaction().commit();

                log("Run 2 end");
            }
        };

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);

        thread1.start();
        wait(1);
        thread2.start();

        thread1.join();
        thread2.join();

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertTrue(product.getDescription().startsWith("Detail description 2"));
        }

        log("End test");
    }

    @Test
    public void usePessimisticWriteLockTest() throws InterruptedException {
        Runnable run1 = () -> {
            log("Start run 1");

            String newDescription = "Detail description 1. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 1 get product kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"), LockModeType.PESSIMISTIC_WRITE);

                log("Run 1 change description");
                product.setDescription(newDescription);

                log("Run 1 wait 3 seconds");
                wait(3);

                log("Run 1 commit");
                entityManager.getTransaction().commit();

                log("Run 1 end");
            }
        };

        Runnable run2 = () -> {
            log("Start run 2");

            String newDescription = "Detail description 2. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 2 get product kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"), LockModeType.PESSIMISTIC_WRITE);
                // If use lock PESSIMISTIC_WRITE, select wait the transaction 1 end
                // Without the lock, update wait the transaction 1 end
                // Because transaction 1 acquire the lock in select

                log("Run 2 change description");
                product.setDescription(newDescription);

                log("Run 2 commit");
                entityManager.getTransaction().commit();

                log("Run 2 end");
            }
        };

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);

        thread1.start();
        wait(1);
        thread2.start();

        thread1.join();
        thread2.join();

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertTrue(product.getDescription().startsWith("Detail description 2"));
        }

        log("End test");
    }

    @Test
    public void mixLocksTest() throws InterruptedException {
        Runnable run1 = () -> {
            log("Start run 1");

            String newDescription = "Detail description 1. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 1 get product kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"), LockModeType.PESSIMISTIC_READ);

                log("Run 1 change description");
                product.setDescription(newDescription);

                log("Run 1 wait 3 seconds");
                wait(3);

                log("Run 1 commit");
                entityManager.getTransaction().commit();

                log("Run 1 end");
            }
        };

        Runnable run2 = () -> {
            log("Start run 2");

            String newDescription = "Detail description 2. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 2 get product kindle");
                Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"), LockModeType.PESSIMISTIC_WRITE);

                log("Run 2 change description");
                product.setDescription(newDescription);

                log("Run 2 commit");
                entityManager.getTransaction().commit();

                log("Run 2 end");
            }
        };

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);

        thread1.start();
        wait(1);
        thread2.start();

        thread1.join();
        thread2.join();

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertTrue(product.getDescription().startsWith("Detail description 2"));
        }

        log("End test");
    }

    @Test
    public void lockWithTypedQueryTest() throws InterruptedException {
        Runnable run1 = () -> {
            log("Start run 1");

            String newDescription = "Detail description 1. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 1 get product kindle");
                TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.stock WHERE p.id = :id", Product.class);
                query.setLockMode(LockModeType.PESSIMISTIC_READ);
                query.setParameter("id", UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
                Product product = query.getSingleResult();

                log("Run 1 change description");
                product.setDescription(newDescription);

                log("Run 1 wait 3 seconds");
                wait(3);

                log("Run 1 commit");
                entityManager.getTransaction().commit();

                log("Run 1 end");
            }
        };

        Runnable run2 = () -> {
            log("Start run 2");

            String newDescription = "Detail description 2. CTM: " + System.currentTimeMillis();

            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();

                log("Run 2 get product kindle");
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
                Root<Product> root = criteriaQuery.from(Product.class);
                root.fetch(Product_.stock, JoinType.LEFT);

                criteriaQuery.select(root);
                criteriaQuery.where(criteriaBuilder.equal(root.get("id"), UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a")));

                TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
                query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
                Product product = query.getSingleResult();

                log("Run 2 change description");
                product.setDescription(newDescription);

                log("Run 2 commit");
                entityManager.getTransaction().commit();

                log("Run 2 end");
            }
        };

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);

        thread1.start();
        wait(1);
        thread2.start();

        thread1.join();
        thread2.join();

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
            Assertions.assertTrue(product.getDescription().startsWith("Detail description 2"));
        }

        log("End test");
    }
}
