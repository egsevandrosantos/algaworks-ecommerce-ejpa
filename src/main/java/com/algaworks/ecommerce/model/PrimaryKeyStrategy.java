package com.algaworks.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "primary_key_strategies")
public class PrimaryKeyStrategy {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "gen")
	@TableGenerator(name = "gen", table = "hibernate_sequences", pkColumnName = "sequence_name", pkColumnValue = "primary_key_strategy_id", valueColumnName = "next_val", initialValue = 0, allocationSize = 50)
	private Integer id;
}
