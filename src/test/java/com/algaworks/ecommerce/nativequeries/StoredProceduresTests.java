package com.algaworks.ecommerce.nativequeries;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class StoredProceduresTests extends EntityManagerTests {
    @Test
    public void testProcedureInOut() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("get_product_name");
        storedProcedureQuery.registerStoredProcedureParameter("product_id", UUID.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("product_name", String.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", UUID.fromString("849c840b-63fa-44b8-9883-47d9940adf8b"));
        storedProcedureQuery.execute();
        String name = (String) storedProcedureQuery.getOutputParameterValue("product_name");
        Assertions.assertEquals("PS4", name);
    }

    @Test
    public void testProcedureWithResultList() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("buy_greater_than_avg", Client.class);
        storedProcedureQuery.registerStoredProcedureParameter("year", Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("year", 2025);
        storedProcedureQuery.execute();
        List<Client> items = storedProcedureQuery.getResultList();
        Assertions.assertFalse(items.isEmpty());
    }
}
