package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
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
@Table(name = "orders")
public class Order {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private Instant orderedAt;
	private Instant finishedAt;
	private UUID invoiceId;
	private BigDecimal total;
	private OrderStatus status;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Order other = (Order) obj;
		return Objects.equals(orderedAt, other.orderedAt)
			&& Objects.equals(finishedAt, other.finishedAt)
			&& Objects.equals(invoiceId, other.invoiceId)
			&& Objects.equals(total, other.total)
			&& Objects.equals(status, other.status);
	}
}
