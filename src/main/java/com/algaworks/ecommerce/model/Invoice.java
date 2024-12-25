package com.algaworks.ecommerce.model;

import java.time.Instant;
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
@Table(name = "invoices")
public class Invoice {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private UUID orderId;
	private String xml;
	private Instant emissionDate;
}
