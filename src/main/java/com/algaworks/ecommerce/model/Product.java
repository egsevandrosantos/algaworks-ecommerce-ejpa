package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;
	@Column(name = "updated_at", insertable = false) // For tests purpose
	private Instant updatedAt;
	@ManyToMany
	@JoinTable(
		name = "products_categories",
		joinColumns = @JoinColumn(name = "product_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private List<Category> categories;
	@OneToOne(mappedBy = "product")
	private Stock stock;
	@ElementCollection
	@CollectionTable(
		name = "products_tags", // Name of table
		joinColumns = @JoinColumn(name = "product_id") // this.id to product_id in another table 
	)
	@Column(name = "tag") // Name to column in another table
	private List<String> tags;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Product other = (Product) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(description, other.description)
			&& Objects.equals(price, other.price);
	}
}
