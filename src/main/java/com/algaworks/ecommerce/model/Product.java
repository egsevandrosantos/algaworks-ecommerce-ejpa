package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(
	name = "products",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "unq_name",
			columnNames = { "name" }
		)
	},
	indexes = {
		@Index(
			name = "idx_name",
			columnList = "name"
		)
	}
)
public class Product extends BaseEntityId {
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
	@ElementCollection
	@CollectionTable(
		name = "products_attributes",
		joinColumns = @JoinColumn(name = "product_id")
	)
	private List<Attribute> attributes;
	@Lob
	private byte[] photo;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Product other = (Product) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(description, other.description)
			&& Objects.equals(price, other.price)
			&& Objects.deepEquals(photo, other.photo);
	}
}
