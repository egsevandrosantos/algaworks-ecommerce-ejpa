package com.algaworks.ecommerce.nativequeries;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Product;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NativeQueryTests extends EntityManagerTests {
    @Test
    public void testSelectAllFromProducts() {
        String sql = "SELECT * FROM products";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        items.forEach(arr -> System.out.println(String.join("  -->  ", Arrays.stream(arr).map(Objects::toString).toList())));
    }

    @Test
    public void testSelectAllFromProductsWithType() {
        String sql = "SELECT * FROM products";
        Query query = entityManager.createNativeQuery(sql, Product.class);
        List<Product> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Product item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testParametersInQuery() {
        String sql = "SELECT * FROM products WHERE name = :name";
        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter("name", "PS5");
        List<Product> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Product item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testSqlResultSetMapping1() {
        String sql = "SELECT * FROM products";
        Query query = entityManager.createNativeQuery(sql, "Product");
        List<Product> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Product item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testSqlResultSetMapping2() {
        String sql = "SELECT oi.*, p.* FROM order_items oi JOIN products p ON oi.product_id = p.id";
        Query query = entityManager.createNativeQuery(sql, "OrderItem_Product");
        List<Object[]> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Object[] item : items) {
            System.out.println("Type 1: " + item[0].getClass().getName() + ", Type 2: " + item[1].getClass().getName());
        }
    }

    @Test
    public void testFieldResult() {
        String sql = "SELECT * FROM ecm_products";
        Query query = entityManager.createNativeQuery(sql, "ecm.Product");
        List<Product> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Product item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testColumnResultToDTO() {
        String sql = "SELECT * FROM ecm_products";
        Query query = entityManager.createNativeQuery(sql, "ecm.ProductDTO");
        List<ProductDTO> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (ProductDTO item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testNamedQuery1() {
        Query query = entityManager.createNamedQuery("products.select_all", Product.class);
        List<Product> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Product item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testNamedQuery2() {
        Query query = entityManager.createNamedQuery("ecm_products.select_all", Product.class);
        List<Product> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Product item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }

    @Test
    public void testNamedQueryInXml() {
        Query query = entityManager.createNamedQuery("ecm_categories.selectAll", Category.class);
        List<Category> items = query.getResultList();
        Assertions.assertFalse(items.isEmpty());
        for (Category item : items) {
            System.out.println("Id: " + item.getId() + ", Name: " + item.getName());
        }
    }
}
