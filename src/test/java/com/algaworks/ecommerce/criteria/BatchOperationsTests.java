package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Category_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class BatchOperationsTests extends EntityManagerTests {
    private static final BigDecimal A_HUNDRED_PER_CENT = new BigDecimal("1"); // 100 / 100
    private static final BigDecimal TEN_PER_CENT = new BigDecimal("0.1"); // 10 / 100

    @Test
    public void testBatchUpdate() {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Product.class);
        Root<Product> root = criteriaUpdate.from(Product.class);

        criteriaUpdate.set(root.get(Product_.price), criteriaBuilder.prod(root.get(Product_.price), A_HUNDRED_PER_CENT.add(TEN_PER_CENT)));

        Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
        // Root<Product> rootSubquery = subquery.from(Product.class); // You can't specify target table 'products' for update in FROM clause
        Root<Product> rootSubquery = subquery.correlate(root);
        Join<Product, Category> joinCategorySubquery = rootSubquery.join(Product_.categories);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(joinCategorySubquery.get(Category_.id), UUID.fromString("d9e5d6f8-6605-4dcd-a21a-3839407a0a1f")));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        Query query = entityManager.createQuery(criteriaUpdate);
        int updated = query.executeUpdate();
        Assertions.assertNotEquals(0, updated);
        System.out.println(updated);

        entityManager.getTransaction().commit();
    }

    @Test
    public void testBatchDelete() {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<Product> criteriaDelete = criteriaBuilder.createCriteriaDelete(Product.class);
        Root<Product> root = criteriaDelete.from(Product.class);
        criteriaDelete.where(
            criteriaBuilder.equal(root.get(Product_.id), UUID.fromString("849c840b-63fa-44b8-9883-47d9940adf8b"))
        );

        Query query = entityManager.createQuery(criteriaDelete);
        int deleted = query.executeUpdate();
        Assertions.assertNotEquals(0, deleted);
        System.out.println(deleted);

        entityManager.getTransaction().commit();
    }
}
