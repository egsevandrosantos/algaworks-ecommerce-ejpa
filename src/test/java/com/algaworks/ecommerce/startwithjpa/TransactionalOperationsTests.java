package com.algaworks.ecommerce.startwithjpa;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Product;

public class TransactionalOperationsTests extends EntityManagerTests {
	@Test
	public void beginAndCloseTransactionWithCommit() {
		entityManager.getTransaction().begin();
		
		// Do operations
		//entityManager.persist(entity);
		//entityManager.merge(entity);
		//entityManager.remove(entity);
		
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void beginAndCloseTransactionWithRollback() {
		entityManager.getTransaction().begin();
		
		// Do operations
		//entityManager.persist(entity);
		//entityManager.merge(entity);
		//entityManager.remove(entity);
		
		entityManager.getTransaction().rollback();
	}
	
	@Test
	public void insertProduct() {
		Product expectedProduct = new Product();
		expectedProduct.setName("Camera Canon");
		expectedProduct.setDescription("A melhor definição para suas fotos.");
		expectedProduct.setPrice(new BigDecimal("5000.00"));
		
		// Out of transaction the code works because persist method wait for begin transaction
		// But if no transaction initialize then insert query is not executed
		// entityManager.persist(expectedProduct);
		
		entityManager.getTransaction().begin();
		entityManager.persist(expectedProduct);
		// Get unflushed objects in entityManager memory and send to database
		// The commit transaction method execute the flush method automatically
		// If persist and flush out of transaction an exception is thrown (TransactionRequireException)
		// entityManager.flush();
		entityManager.getTransaction().commit();
		
		// Clear memory of entityManager (to remove expectedProduct from memory and select query execute)
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, expectedProduct.getId());
		Assertions.assertTrue(expectedProduct.fullEquals(actualProduct));
	}
	
	@Test
	public void removeProduct() {
		Product productToRemove = entityManager.find(Product.class, UUID.fromString("77c31aa8-14f5-4df1-9a96-fa03d6882f4f"));

		// Out of transaction the code works because remove method wait for begin transaction
		// But if no transaction initialize then delete query is not executed 
		// entityManager.remove(productToRemove);
		
		entityManager.getTransaction().begin();
		entityManager.remove(productToRemove);
		entityManager.getTransaction().commit();
		
		// If remove executed out of transaction and clear is not executed
		// Find return null but object exists in database (delete query is not executed)
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, productToRemove.getId());
		Assertions.assertNull(actualProduct);
	}
	
	@Test
	public void updateProduct() {
		Product productToUpdate = new Product();
		productToUpdate.setId(UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		productToUpdate.setName("Kindle Paperwhite");
		productToUpdate.setDescription("Conheça o novo Kindle");
		productToUpdate.setPrice(new BigDecimal("599.00"));
		
		// Out of transaction the code works because merge method wait for begin transaction
		// But if no transaction initialize then update query is not executed
		// entityManager.merge(productToUpdate);
		
		entityManager.getTransaction().begin();
		entityManager.merge(productToUpdate);
		entityManager.getTransaction().commit();
		
		// If merge executed out of transaction and clear is not executed
		// Find return updated object but in the database is the previous object
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, productToUpdate.getId());
		Assertions.assertTrue(productToUpdate.fullEquals(actualProduct));
	}
	
	@Test
	public void updateProductWithManagedObject() {
		Product productToUpdate = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		// Out of transaction the code works because productToUpdate is a object managed by entity manager
		// But if no transaction initialize then update query is not executed
		// Is not necessary merge method because entity manager listen the change in object and apply automatically
		// productToUpdate.setName("Kindle Paperwhite 2º generation");

		entityManager.getTransaction().begin();
		productToUpdate.setName("Kindle Paperwhite 2º generation");
		entityManager.getTransaction().commit();
		
		// If not exists one transaction and clear is not executed
		// Find return updated object but in the database is the previous object
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, productToUpdate.getId());
		Assertions.assertTrue(productToUpdate.fullEquals(actualProduct));
	}
	
	@Test
	public void insertProductWithMerge() {
		Product expectedProduct = new Product();
		expectedProduct.setName("Microphone Rode Videmic");
		expectedProduct.setDescription("A melhor qualidade de som.");
		expectedProduct.setPrice(new BigDecimal("1000.00"));
		
		// Out of transaction the code works because merge method wait for begin transaction
		// But if no transaction initialize then insert query is not executed
		// entityManager.merge(expectedProduct);
		
		entityManager.getTransaction().begin();
		// Execute select in database by id to recover object if exists
		// If exits then execute update else execute insert
		expectedProduct = entityManager.merge(expectedProduct);
		entityManager.getTransaction().commit();
		
		// Clear memory of entityManager (to remove expectedProduct from memory and select query execute)
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, expectedProduct.getId());
		Assertions.assertTrue(expectedProduct.fullEquals(actualProduct));
	}
	
	@Test
	public void differencesPersistenceAndMerge() {
		{
			Product productToPersist = new Product();
			productToPersist.setName("Smartphone One Plus");
			productToPersist.setDescription("O processador mais rápido");
			productToPersist.setPrice(new BigDecimal("2000.00"));
		
			entityManager.getTransaction().begin();
			// Insert product and managed by entity manager
			entityManager.persist(productToPersist);
			// Update product because is managed by entity manager
			productToPersist.setName("Smartphone Two Plus");
			entityManager.getTransaction().commit();
			
			entityManager.clear();
			
			Product actualProduct = entityManager.find(Product.class, productToPersist.getId());
			Assertions.assertTrue(productToPersist.fullEquals(actualProduct));
		}
		
		{
			Product productToMerge = new Product();
			productToMerge.setName("Notebook Dell");
			productToMerge.setDescription("O melhor da categoria");
			productToMerge.setPrice(new BigDecimal("2000.00"));
			
			entityManager.getTransaction().begin();
			// Select to get object if exists and add a copy of object to managed by entity manager
			// If exists then execute insert else execute update
			// entityManager.merge(productToMerge);
			// To get the copy, replace entityManager.merge(productToMerge); to:
			productToMerge = entityManager.merge(productToMerge);
			// Update product only if productToMerge is the copy (object managed by entity manager)
			productToMerge.setName("Notebook Dell Two");
			entityManager.getTransaction().commit();
			
			entityManager.clear();
			
			Product actualProduct = entityManager.find(Product.class, productToMerge.getId());
			Assertions.assertTrue(productToMerge.fullEquals(actualProduct));
		}
	}
	
	@Test
	public void detachManagedObject() {
		Product productToUpdate = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		// Remove (detach) object from entity manager memory
		// After this point the object is not managed by entity manager
		entityManager.detach(productToUpdate);

		entityManager.getTransaction().begin();
		productToUpdate.setName("Kindle Paperwhite 3º generation");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, productToUpdate.getId());
		Assertions.assertNotEquals("Kindle Paperwhite 3º generation", actualProduct.getName());
	}
}
