package com.algaworks.ecommerce.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
// @Table(name = "bank_slip_payments") // Commented because has inheritance with payment and this mapping create a one table with all properties
public class BankSlipPayment extends Payment {
	private String barcode;
	
	@Override
	public boolean fullEquals(Object obj) {
		if (!super.fullEquals(obj)) return false;
		
		BankSlipPayment other = (BankSlipPayment) obj;
		return Objects.equals(barcode, other.barcode);
	}
}
