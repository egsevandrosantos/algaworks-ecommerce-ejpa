package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
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
@Table(name = "order_items")
public class OrderItem {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private UUID orderId;
	private UUID productId;
	private BigDecimal productPrice;
	private Integer quantity;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		OrderItem other = (OrderItem) obj;
		return Objects.equals(orderId, other.orderId)
			&& Objects.equals(productId, other.productId)
			&& Objects.equals(productPrice, other.productPrice)
			&& Objects.equals(quantity, other.quantity);
	}
}
