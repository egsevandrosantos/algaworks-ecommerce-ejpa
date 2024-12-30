package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.util.UUID;

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
public class OrderItemId implements Serializable {
	private static final long serialVersionUID = -121626929185409064L;

	@EqualsAndHashCode.Include
	private UUID orderId;
	
	@EqualsAndHashCode.Include
	private UUID productId;
}
