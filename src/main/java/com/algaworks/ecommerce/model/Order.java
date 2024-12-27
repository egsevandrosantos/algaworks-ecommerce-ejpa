package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "ordered_at")
	private Instant orderedAt;
	@Column(name = "finished_at")
	private Instant finishedAt;
	@Column(name = "invoice_id")
	private UUID invoiceId;
	private BigDecimal total;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@Embedded
	private OrderAddress address;
	
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Order other = (Order) obj;
		
		DateTimeFormatter instantFormatter = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
		return Objects.equals(Optional.ofNullable(orderedAt).map(instantFormatter::format), Optional.ofNullable(other.orderedAt).map(instantFormatter::format))
			&& Objects.equals(Optional.ofNullable(finishedAt).map(instantFormatter::format), Optional.ofNullable(other.finishedAt).map(instantFormatter::format))
			&& Objects.equals(invoiceId, other.invoiceId)
			&& Objects.equals(total, other.total)
			&& Objects.equals(status, other.status)
			&& Objects.equals(address, other.address);
	}
}
