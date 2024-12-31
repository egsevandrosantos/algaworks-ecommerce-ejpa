package com.algaworks.ecommerce.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "bank_slip_payments")
public class BankSlipPayment extends BaseEntityId {
	@Column(name = "order_id")
	private UUID orderId;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	private String barcode;
}
