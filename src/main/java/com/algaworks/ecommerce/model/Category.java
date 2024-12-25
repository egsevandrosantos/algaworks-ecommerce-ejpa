package com.algaworks.ecommerce.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
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
@Table(name = "categories")
public class Category {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private String name;
	@Column(name = "parent_id")
	private UUID parentId;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Category other = (Category) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(parentId, other.parentId);
	}
}
