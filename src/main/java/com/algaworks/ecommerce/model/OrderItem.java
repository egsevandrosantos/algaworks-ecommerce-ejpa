package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order_items")
public class OrderItem {
	@EqualsAndHashCode.Include
	@EmbeddedId
	private OrderItemId id;
	@MapsId("orderId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id") // , insertable = false, updatable = false) -> Unnecessary with @MapsId
	private Order order;
	@MapsId("productId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id") // , insertable = false, updatable = false) -> Unnecessary with @MapsId
	private Product product;
	@Column(name = "product_price")
	private BigDecimal productPrice;
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
