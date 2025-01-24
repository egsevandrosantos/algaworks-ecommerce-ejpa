package com.algaworks.ecommerce.nativequeries;

import com.algaworks.ecommerce.EntityManagerTests;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NativeQueryTests extends EntityManagerTests {
    @Test
    public void test() {
        String sql = "SELECT * FROM products";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Objects::toString).toList())));
    }
}
