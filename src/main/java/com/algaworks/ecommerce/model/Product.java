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
@Table(name = "products")
public class Product {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Product other = (Product) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(description, other.description)
			&& Objects.equals(price, other.price);
	}
}
