package com.algaworks.ecommerce.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "card_payments")
public class CardPayment {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	@Column(name = "card_number")
	private String cardNumber;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		CardPayment other = (CardPayment) obj;
		return Objects.equals(Optional.ofNullable(order).map(Order::getId), Optional.ofNullable(other.order).map(Order::getId))
			&& Objects.equals(status, other.status)
			&& Objects.equals(cardNumber, other.cardNumber);
	}
}
