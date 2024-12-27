package com.algaworks.ecommerce.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "stocks")
public class Stock {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "product_id")
	private UUID productId;
	private Integer quantity;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Stock other = (Stock) obj;
		return Objects.equals(productId, other.productId)
			&& Objects.equals(quantity, other.quantity);
	}
}
