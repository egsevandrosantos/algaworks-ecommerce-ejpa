package com.algaworks.ecommerce.model;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "invoices")
public class Invoice {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@OneToOne(optional = false)
	@JoinColumn(name = "order_id")
	// OneToOne with JoinTable example (in this case don't use @JoinColumn)
	// @JoinTable(
		// name = "invoices_orders",
		// joinColumns = @JoinColumn(name = "invoice_id", unique = true),
		// inverseJoinColumns = @JoinColumn(name = "order_id", unique = true)
	// )
	private Order order;
	private String xml;
	@Column(name = "emission_date")
	private Instant emissionDate;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Invoice other = (Invoice) obj;
		DateTimeFormatter instantFormatter = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
		return Objects.equals(Optional.ofNullable(order).map(Order::getId), Optional.ofNullable(other.order).map(Order::getId))
			&& Objects.equals(xml, other.xml)
			&& Objects.equals(Optional.ofNullable(emissionDate).map(instantFormatter::format), Optional.ofNullable(other.emissionDate).map(instantFormatter::format));
	}
}
