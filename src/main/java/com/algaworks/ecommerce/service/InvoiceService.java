package com.algaworks.ecommerce.service;

import java.time.Instant;

import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;

public class InvoiceService {
	public void generate(Order order) {
		System.out.println("Generating invoice to order " + order.getId());
		
		Invoice invoice = new Invoice();
		invoice.setOrder(order);
		invoice.setXml("<?xml />");
		invoice.setEmissionDate(Instant.now());
		
		order.setInvoice(invoice);
	}
}
