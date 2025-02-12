package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.model.converter.BooleanYesNoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "Product", entities = {@EntityResult(entityClass = Product.class)}),
	@SqlResultSetMapping(
		name = "ecm.Product",
		entities = {
			@EntityResult(
				entityClass = Product.class,
				fields = {
					@FieldResult(name = "id", column = "product_id"),
					@FieldResult(name = "name", column = "product_name"),
					@FieldResult(name = "description", column = "product_description"),
					@FieldResult(name = "price", column = "product_price"),
					@FieldResult(name = "photo", column = "product_photo"),
					@FieldResult(name = "createdAt", column = "product_created_at"),
					@FieldResult(name = "updatedAt", column = "product_updated_at"),
				}
			)
		}
	),
	@SqlResultSetMapping(
		name = "ecm.ProductDTO",
		classes = {
			@ConstructorResult(
				targetClass = ProductDTO.class,
				columns = {
					@ColumnResult(name = "product_id", type = UUID.class),
					@ColumnResult(name = "product_name", type = String.class)
				}
			)
		}
	)
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "products.select_all", query = "SELECT * FROM products", resultClass = Product.class),
	@NamedNativeQuery(name = "ecm_products.select_all", query = "SELECT * FROM ecm_products", resultSetMapping = "ecm.Product")
})
public class Product extends BaseEntityId {
	// Without @Column with customizations default is VARCHAR(255)
	// With this customization: VARCHAR(100) NOT NULL
	// unique = true -> Create a unique constraint (name for index generate auto and only for single attribute)
	@NotBlank
	@Column(length = 100, nullable = false)
	private String name;

	// columnDefinition = SQL
	// @Column(columnDefinition = "VARCHAR(275) NOT NULL DEFAULT 'description'")
	@Lob // longtext
	private String description;

	// 10 are total, 8 integers and 2 decimals
	@Positive
	@Column(precision = 10, scale = 2) // DECIMAL(10,2)
	private BigDecimal price;

	@PastOrPresent
	@NotNull
	@Column(name = "created_at", updatable = false, nullable = false)
	private Instant createdAt;

	@PastOrPresent
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

	@Convert(converter = BooleanYesNoConverter.class)
	@NotNull
	@Column(nullable = false, length = 3)
	private boolean active = Boolean.FALSE;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Product other = (Product) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(description, other.description)
			&& Objects.equals(price, other.price)
			&& Objects.deepEquals(photo, other.photo);
	}
}
