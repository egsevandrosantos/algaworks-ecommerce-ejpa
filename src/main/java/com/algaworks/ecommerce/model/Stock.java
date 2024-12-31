package com.algaworks.ecommerce.model;

import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "stocks")
public class Stock extends BaseEntityId {
	@OneToOne(optional = false)
	@JoinColumn(name = "product_id")
	private Product product;
	private Integer quantity;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Stock other = (Stock) obj;
		return Objects.equals(Optional.ofNullable(product).map(Product::getId), Optional.ofNullable(other.product).map(Product::getId))
			&& Objects.equals(quantity, other.quantity);
	}
}
