package com.algaworks.ecommerce.relationship;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Category;

public class CategoryRelationshipsTests extends EntityManagerTests {
	@Test
	public void testParentRelationship() {
		Category parent = new Category();
		parent.setName("Electronics");
		
		Category child = new Category();
		child.setName("Cellphones");
		
		child.setParent(parent);
		parent.setChildren(List.of(child));
		
		entityManager.getTransaction().begin();
		entityManager.persist(parent);
		entityManager.persist(child);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category actualParent = entityManager.find(Category.class, parent.getId());
		Assertions.assertTrue(parent.fullEquals(actualParent) && actualParent.getChildren().size() == 1 && Objects.equals(child.getId(), actualParent.getChildren().get(0).getId()));
	}
	
	@Test
	public void testChildrenRelationship() {
		Category parent = new Category();
		parent.setName("Electronics");
		
		Category child = new Category();
		child.setName("Cellphones");
		
		child.setParent(parent);
		parent.setChildren(List.of(child));
		
		entityManager.getTransaction().begin();
		entityManager.persist(parent);
		entityManager.persist(child);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category actualChild = entityManager.find(Category.class, child.getId());
		Assertions.assertTrue(child.fullEquals(actualChild) && actualChild.getParent() != null && Objects.equals(parent.getId(), actualChild.getParent().getId()));
	}
}
