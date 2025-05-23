package com.algaworks.ecommerce.model;

import java.sql.Types;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntityId {
	@NotNull
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_invoice_order")) // , insertable = false, updatable = false) -> Unnecessary with @MapsId
	// OneToOne with JoinTable example (in this case don't use @JoinColumn)
	// @JoinTable(
		// name = "invoices_orders",
		// joinColumns = @JoinColumn(name = "invoice_id", unique = true),
		// inverseJoinColumns = @JoinColumn(name = "order_id", unique = true)
	// )
	private Order order;

	@NotEmpty
	@Lob
	// /* MySQL */ @Column(columnDefinition = "LONGBLOB NOT NULL")
	@JdbcTypeCode(Types.VARBINARY)
	private byte[] xml;

	// If use Date type, use TemporalType.TIMESTAMP
	@NotNull
	@PastOrPresent
	@Column(name = "emission_date", nullable = false)
	private Instant emissionDate;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Invoice other = (Invoice) obj;
		DateTimeFormatter instantFormatter = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
		return Objects.equals(Optional.ofNullable(order).map(Order::getId), Optional.ofNullable(other.order).map(Order::getId))
			&& Objects.deepEquals(xml, other.xml)
			&& Objects.equals(Optional.ofNullable(emissionDate).map(instantFormatter::format), Optional.ofNullable(other.emissionDate).map(instantFormatter::format));
	}
}
