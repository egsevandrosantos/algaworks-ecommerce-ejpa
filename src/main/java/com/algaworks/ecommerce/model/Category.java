package com.algaworks.ecommerce.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(
	name = "categories",
	indexes = {
		@Index(
			name = "idx_name",
			columnList = "name",
			unique = true
		)
	}
)
public class Category extends BaseEntityId {
	private String name;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	@OneToMany(mappedBy = "parent")
	private List<Category> children;
	@ManyToMany(mappedBy = "categories")
	private List<Product> products;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Category other = (Category) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(Optional.ofNullable(parent).map(Category::getId), Optional.ofNullable(other.parent).map(Category::getId));
	}
}
