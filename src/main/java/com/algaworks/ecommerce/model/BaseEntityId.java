package com.algaworks.ecommerce.model;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class BaseEntityId {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	// /* MySQL */ @GeneratedValue(strategy = GenerationType.IDENTITY)
	// /* Postgres */ @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private UUID id;

//	@Version
	private Integer version;

	@Column(nullable = false)
	@NotBlank
	private String tenant;
}
