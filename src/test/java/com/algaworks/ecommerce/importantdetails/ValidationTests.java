package com.algaworks.ecommerce.importantdetails;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import org.junit.jupiter.api.Test;

public class ValidationTests extends EntityManagerTests {
    @Test
    public void testValidateClient() {
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setCpf("");
        client.setName("");
        entityManager.persist(client);

        entityManager.getTransaction().commit();
    }
}
