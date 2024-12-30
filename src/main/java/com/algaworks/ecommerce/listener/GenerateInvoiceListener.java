package com.algaworks.ecommerce.listener;

import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.service.InvoiceService;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class GenerateInvoiceListener {
	private final InvoiceService invoiceService = new InvoiceService();

	@PrePersist
	@PreUpdate
	public void generate(Order order) {
		if (!order.isPaid()) return;
		if (order.getInvoice() != null) return;
		
		invoiceService.generate(order);
	}
}
