package com.algaworks.ecommerce.model;

import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
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
	private UUID id;
}
