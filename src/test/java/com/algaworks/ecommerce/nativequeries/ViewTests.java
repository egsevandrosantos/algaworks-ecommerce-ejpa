package com.algaworks.ecommerce.nativequeries;

import com.algaworks.ecommerce.EntityManagerTests;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ViewTests extends EntityManagerTests {
    @Test
    public void testView() {
        String sql = "SELECT c.id, c.name, SUM(o.total) FROM orders o JOIN view_buy_greater_than_avg c ON c.id = o.client_id GROUP BY o.client_id";

        Query query = entityManager.createNativeQuery(sql, Object[].class);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Object::toString).toList())));
    }
}
