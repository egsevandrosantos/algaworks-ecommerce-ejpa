package com.algaworks.ecommerce.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
// @Table(name = "card_payments") // Commented because has inheritance with payment and this mapping create a one table with all properties
public class CardPayment extends Payment {
	@Column(name = "card_number")
	private String cardNumber;
	
	@Override
	public boolean fullEquals(Object obj) {
		if (!super.fullEquals(obj)) return false;
		
		CardPayment other = (CardPayment) obj;
		return Objects.equals(cardNumber, other.cardNumber);
	}
}
