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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "Order.essentialData",
		attributeNodes = {
			@NamedAttributeNode(Order_.CREATED_AT),
			@NamedAttributeNode(Order_.STATUS),
			@NamedAttributeNode(Order_.TOTAL),
			@NamedAttributeNode(value = Order_.CLIENT, subgraph = "client")
		},
		subgraphs = {
			@NamedSubgraph(
				name = "client",
				attributeNodes = {
					@NamedAttributeNode(Client_.NAME),
					@NamedAttributeNode(Client_.CPF)
				}
			)
		}
	)
})
public class Order extends BaseEntityId {
	// CascadeType.PERSIST = Persist client and persist order
	@NotNull
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST) /*(fetch = FetchType.LAZY)*/ // Without @JoinColumn the column name is property name + _ + property id in Client class (client_id)
	@JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_client"))
	private Client client;

	@PastOrPresent
	@NotNull
	@Column(name = "created_at", nullable = false)
	@Setter(value = AccessLevel.NONE)
	private Instant createdAt;

	@PastOrPresent
	@Column(name = "updated_at")
	@Setter(value = AccessLevel.NONE)
	private Instant updatedAt;

	@PastOrPresent
	@Column(name = "finished_at")
	private Instant finishedAt;

	@Positive
	@NotNull
	@Column(precision = 19, scale = 2, nullable = false)
	private BigDecimal total;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@Embedded
	private OrderAddress address;

	// CascadeType.PERSIST = Persist order and persist items
	// CascadeType.MERGE = Merge order and merge items
	// CascadeType.REMOVE = Remove order and remove items
	// @OneToMany and @OneToOne has property orphanRemoval = CascadeType.REMOVE
	// getItems().clear() not delete with CascadeType.REMOVE but with orphanRemoval (because we don't have a remove command, but object is orphan in memory)
	@NotEmpty
	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true) /* CascadeType.REMOVE, fetch = FetchType.EAGER */
	private List<OrderItem> items;

	@OneToOne(mappedBy = "order", fetch = FetchType.LAZY) // Without effect immediately (with optional = false works, but hibernate use join in select)
	private Payment payment;

	@OneToOne(mappedBy = "order", fetch = FetchType.LAZY) // Without effect immediately (with optional = false works, but hibernate use join in select)
	private Invoice invoice;

	// For default optional is true, so Hibernate not known if exists your object
	// Then it loads with Eager and ignore Lazy
	// With optional = false to Hibernate the object exists and use Lazy
	
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
					.map(orderItem -> orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getQuantity())))
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
