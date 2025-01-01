package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.algaworks.ecommerce.listener.GenerateInvoiceListener;
import com.algaworks.ecommerce.listener.LoggingLoadedEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "orders")
@EntityListeners(value = { GenerateInvoiceListener.class, LoggingLoadedEntityListener.class })
public class Order extends BaseEntityId {
	@ManyToOne(optional = false) /*(fetch = FetchType.LAZY)*/ // Without @JoinColumn the column name is property name + _ + property id in Client class (client_id)
	@JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_client"))
	private Client client;
	@Column(name = "created_at", nullable = false)
	@Setter(value = AccessLevel.NONE)
	private Instant createdAt;
	@Column(name = "updated_at")
	@Setter(value = AccessLevel.NONE)
	private Instant updatedAt;
	@Column(name = "finished_at")
	private Instant finishedAt;
	@Column(precision = 19, scale = 2, nullable = false)
	private BigDecimal total;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;
	@Embedded
	private OrderAddress address;
	@OneToMany(mappedBy = "order") /*fetch = FetchType.EAGER*/
	private List<OrderItem> items;
	@OneToOne(mappedBy = "order")
	private Payment payment;
	@OneToOne(mappedBy = "order")
	private Invoice invoice;
	
	public boolean isPaid() {
		return Objects.equals(status, OrderStatus.PAID);
	}
	
	// @PrePersist // Only one for entity
	// @PreUpdate // Only one for entity
	public void calculateTotal() {
		total = BigDecimal.ZERO;
		Optional.ofNullable(items)
			.ifPresent(items -> {
				total = items.stream()
					.map(OrderItem::getProductPrice)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			});
	}
	
	private void auditingEntity() {
		Instant now = Instant.now();
		this.createdAt = Optional.ofNullable(this.createdAt).orElse(now);
		this.updatedAt = now;
	}
	
	@PrePersist // Only one for entity
	public void prePersist() {
		System.out.println("Pre persist");
		this.auditingEntity();
		this.calculateTotal();
	}
	
	@PreUpdate // Only one for entity
	public void preUpdate() {
		System.out.println("Pre update");
		this.auditingEntity();
		this.calculateTotal();
	}
	
	@PostPersist // Only one for entity
	public void postPersist() {
		System.out.println("Post persist");
	}
	
	@PostUpdate // Only one for entity
	public void postUpdate() {
		System.out.println("Post update");
	}
	
	@PreRemove // Only one for entity
	public void preRemove() {
		System.out.println("Pre remove");
	}
	
	@PostRemove // Only one for entity
	public void postRemove() {
		System.out.println("Post remove");
	}
	
	@PostLoad // Only one for entity
	public void postLoad() {
		System.out.println("Post load");
	}
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Order other = (Order) obj;
		
		DateTimeFormatter instantFormatter = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
		return Objects.equals(Optional.ofNullable(createdAt).map(instantFormatter::format), Optional.ofNullable(other.createdAt).map(instantFormatter::format))
			&& Objects.equals(Optional.ofNullable(updatedAt).map(instantFormatter::format), Optional.ofNullable(other.updatedAt).map(instantFormatter::format))
			&& Objects.equals(Optional.ofNullable(finishedAt).map(instantFormatter::format), Optional.ofNullable(other.finishedAt).map(instantFormatter::format))
			&& Optional.ofNullable(total).map(total -> total.compareTo(other.total) == 0).orElse(total == other.total)
			&& Objects.equals(status, other.status)
			&& Objects.equals(address, other.address);
	}
}
