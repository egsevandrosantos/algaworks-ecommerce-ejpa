package com.algaworks.ecommerce.nativequeries;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Client;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class StoredProceduresTests extends EntityManagerTests {
    private static final BigDecimal TEN_PER_CENT = new BigDecimal("0.1"); // 10 / 100 (10%)
    private static final BigDecimal HUNDRED_PER_CENT = BigDecimal.ONE; // 100 / 100 (100%)

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

    @Test
    public void testProcedureFixingProductPrice() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("fixing_product_price");
        storedProcedureQuery.registerStoredProcedureParameter("product_id", UUID.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("percentage_fixing", BigDecimal.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("new_price", BigDecimal.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", UUID.fromString("849c840b-63fa-44b8-9883-47d9940adf8b"));
        storedProcedureQuery.setParameter("percentage_fixing", TEN_PER_CENT);

        storedProcedureQuery.execute();
        BigDecimal newPrice = (BigDecimal) storedProcedureQuery.getOutputParameterValue("new_price");
        BigDecimal expectedPrice = new BigDecimal("1500.00").multiply(HUNDRED_PER_CENT.add(TEN_PER_CENT));
        Assertions.assertEquals(0, expectedPrice.compareTo(newPrice));
    }

    @Test
    public void testNamedStoredProcedureQuery() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("Client.buy_greater_than_avg");

        storedProcedureQuery.setParameter("year", 2025);
        storedProcedureQuery.execute();
        List<Client> items = storedProcedureQuery.getResultList();
        Assertions.assertFalse(items.isEmpty());
    }
}
