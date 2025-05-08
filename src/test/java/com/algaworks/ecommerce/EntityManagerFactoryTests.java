package com.algaworks.ecommerce;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class EntityManagerFactoryTests {
    protected static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterAll
    public static void afterAll() {
        entityManagerFactory.close();
    }

    protected static void log(Object obj, Object... args) {
        System.out.printf("[LOG " + System.currentTimeMillis() + "] " + obj + "%n", args);
    }

    protected static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignored) {}
    }
}
