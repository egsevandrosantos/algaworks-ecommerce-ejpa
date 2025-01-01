package com.algaworks.ecommerce.model;

import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "payments") // Used in inheritance with SINGLE_TABLE and JOINED
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Default SINGLE_TABLE for inheritance if not has configuration
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING) // Default if not has configuration. Used for inheritance with SINGLE_TABLE and JOINED
public abstract class Payment extends BaseEntityId {
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "order_id")
	private Order order;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Payment other = (Payment) obj;
		return Objects.equals(Optional.ofNullable(order).map(Order::getId), Optional.ofNullable(other.order).map(Order::getId))
			&& Objects.equals(status, other.status);
	}
}
