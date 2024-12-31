package com.algaworks.ecommerce.advancedmapping;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTests;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Product;

public class SaveFileTests extends EntityManagerTests {
	@Test
	public void testSaveInvoiceXML() {
		Order order = entityManager.find(Order.class, UUID.fromString("24be65bf-8e80-477c-81c5-277697b1bd37"));
		
		Invoice invoice = new Invoice();
		invoice.setOrder(order);
		invoice.setEmissionDate(Instant.now());
		invoice.setXml(loadInvoiceExample());
		
		entityManager.getTransaction().begin();
		entityManager.persist(invoice);
		entityManager.getTransaction().commit();
		
		Invoice actualInvoice = entityManager.find(Invoice.class, invoice.getId());
		
		// Save byte[] to file
		/*try (
			OutputStream out = new FileOutputStream(
				Files.createFile(
					Paths.get(System.getProperty("user.home") + "/invoice.xml")
				).toFile()
			)
		) {
			out.write(actualInvoice.getXml());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}*/
		
		Assertions.assertTrue(invoice.fullEquals(actualInvoice));
	}
	
	@Test
	public void testSaveProductPhoto() {
		Product product = entityManager.find(Product.class, UUID.fromString("ab5666b6-3106-469b-9e34-2963b801466a"));
		
		entityManager.getTransaction().begin();
		product.setPhoto(loadPhoto());
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product actualProduct = entityManager.find(Product.class, product.getId());
		Assertions.assertTrue(product.fullEquals(actualProduct));
	}
	
	private byte[] loadInvoiceExample() {
		try {
			return SaveFileTests.class.getResourceAsStream("/invoice.xml").readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private byte[] loadPhoto() {
		try {
			return SaveFileTests.class.getResourceAsStream("/photo.jpg").readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
