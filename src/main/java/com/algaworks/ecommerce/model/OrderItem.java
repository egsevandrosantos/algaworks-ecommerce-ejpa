package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order_items")
@SqlResultSetMapping(
	name = "OrderItem_Product",
	entities = {
		@EntityResult(entityClass = OrderItem.class),
		@EntityResult(entityClass = Product.class)
	}
)
public class OrderItem {
	@EqualsAndHashCode.Include
	@EmbeddedId
	private OrderItemId id;

//	@Version
	private Integer version;

	// Don't need CascadeType.PERSIST to persist item and persist order because order is part of PK (@MapsId), so JPA persist automatically
	// CascadeType.MERGE = Merge order item and merge order
	@NotNull
	@MapsId("orderId")
	@ManyToOne(optional = false) /* cascade = { CascadeType.MERGE, CascadeType.REMOVE } */
	@JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_order")) // , insertable = false, updatable = false) -> Unnecessary with @MapsId
	private Order order;

	@NotNull
	@MapsId("productId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_product")) // , insertable = false, updatable = false) -> Unnecessary with @MapsId
	private Product product;

	@Positive
	@NotNull
	@Column(name = "product_price", precision = 19, scale = 2, nullable = false)
	private BigDecimal productPrice;

	@Positive
	@NotNull
	@Column(nullable = false)
	private Integer quantity;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		OrderItem other = (OrderItem) obj;
		return Objects.equals(Optional.ofNullable(order).map(Order::getId), Optional.ofNullable(other.order).map(Order::getId))
			&& Objects.equals(Optional.ofNullable(product).map(Product::getId), Optional.ofNullable(other.product).map(Product::getId))
			&& Optional.ofNullable(productPrice).map(productPrice -> productPrice.compareTo(other.productPrice) == 0).orElse(productPrice == other.productPrice)
			&& Objects.equals(quantity, other.quantity);
	}
}
