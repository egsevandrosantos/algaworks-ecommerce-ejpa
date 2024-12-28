package com.algaworks.ecommerce.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	@OneToMany(mappedBy = "parent")
	private List<Category> children;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Category other = (Category) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(Optional.ofNullable(parent).map(Category::getId), Optional.ofNullable(other.parent).map(Category::getId));
	}
}
