package com.algaworks.ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Product {
	@EqualsAndHashCode.Include
	@Id
	private Integer id;
	private String name;
	private String description;
	private BigDecimal price;
}
