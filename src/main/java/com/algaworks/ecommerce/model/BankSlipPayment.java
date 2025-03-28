package com.algaworks.ecommerce.model;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
// @Table(name = "bank_slip_payments") // Commented when has inheritance with payment and this mapping create a one table with all properties (SINGLE_TABLE)
@DiscriminatorValue(value = "BankSlipPayment") // Default if not has configuration. Used in inheritance with SINGLE_TABLE and JOINED
public class BankSlipPayment extends Payment {
	@NotBlank
	@Column(length = 100)
	private String barcode;

	@NotNull
	@FutureOrPresent
	@Column(name = "due_date")
	private Instant dueDate;
	
	@Override
	public boolean fullEquals(Object obj) {
		if (!super.fullEquals(obj)) return false;
		
		BankSlipPayment other = (BankSlipPayment) obj;
		return Objects.equals(barcode, other.barcode);
	}
}
