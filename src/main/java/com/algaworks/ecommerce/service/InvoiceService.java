package com.algaworks.ecommerce.service;

import java.io.IOException;
import java.time.Instant;

import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;

public class InvoiceService {
	public void generate(Order order) {
		System.out.println("Generating invoice to order " + order.getId());
		
		Invoice invoice = new Invoice();
		invoice.setOrder(order);
		invoice.setXml(loadInvoiceExample());
		invoice.setEmissionDate(Instant.now());
		
		order.setInvoice(invoice);
	}
	
	private byte[] loadInvoiceExample() {
		try {
			return InvoiceService.class.getResourceAsStream("/invoice.xml").readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
