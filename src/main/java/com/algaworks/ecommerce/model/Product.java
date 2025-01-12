package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NamedQueries({	
	@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
	@NamedQuery(name = "Product.findByCategory", query = "SELECT p FROM Product p WHERE EXISTS (SELECT 1 FROM p.categories c1 WHERE c1.id = :categoryId)")
})
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
	// Without @Column with customizations default is VARCHAR(255)
	// With this customization: VARCHAR(100) NOT NULL
	// unique = true -> Create a unique constraint (name for index generate auto and only for single attribute)
	@Column(length = 100, nullable = false)
	private String name;
	// columnDefinition = SQL
	// @Column(columnDefinition = "VARCHAR(275) NOT NULL DEFAULT 'description'")
	@Lob // longtext
	private String description;
	// 10 are total, 8 integers and 2 decimals
	@Column(precision = 10, scale = 2) // DECIMAL(10,2)
	private BigDecimal price;
	@Column(name = "created_at", updatable = false, nullable = false)
	private Instant createdAt;
	@Column(name = "updated_at", insertable = false) // For tests purpose
	private Instant updatedAt;
	// ManyToMany we remove relationship Product x Category, not the entity in database, and is removed automatically
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "products_categories",
		// foreignKey = @ForeignKey(name = "fk_category_product"),
		// inverseForeignKey = @ForeignKey(name = "fk_product_category"),
		joinColumns = @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_category_product")),
		inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category"))
	)
	private List<Category> categories;
	@OneToOne(mappedBy = "product")
	private Stock stock;
	@ElementCollection
	@CollectionTable(
		name = "products_tags", // Name of table
		joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_tag")) // this.id to product_id in another table 
	)
	@Column(name = "tag", length = 50, nullable = false) // Name to column in another table
	private List<String> tags;
	@ElementCollection
	@CollectionTable(
		name = "products_attributes",
		joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_attribute"))
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
