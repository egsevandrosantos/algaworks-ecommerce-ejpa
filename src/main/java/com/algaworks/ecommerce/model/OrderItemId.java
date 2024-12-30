package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItemId implements Serializable {
	private static final long serialVersionUID = -121626929185409064L;

	@EqualsAndHashCode.Include
	@Column(name = "order_id")
	private UUID orderId;

	@EqualsAndHashCode.Include
	@Column(name = "product_id")
	private UUID productId;
}
