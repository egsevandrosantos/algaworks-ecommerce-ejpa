package com.algaworks.ecommerce.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "bank_slip_payments")
public class BankSlipPayment {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private UUID orderId;
	private PaymentStatus status;
	private String barcode;
}
