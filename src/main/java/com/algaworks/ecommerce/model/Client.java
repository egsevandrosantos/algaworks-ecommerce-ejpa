package com.algaworks.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.algaworks.ecommerce.listener.LoggingLoadedEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(
	name = "clients",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "unq_client_cpf",
			columnNames = { "cpf" }
		) 
	},
	indexes = {
		@Index(
			name = "idx_client_name",
			columnList = "name" // Separated by ',' -> Column name
		)
	}
)
@EntityListeners(value = { LoggingLoadedEntityListener.class })
@SecondaryTable(
	name = "clients_details",
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"),
	foreignKey = @ForeignKey(name = "fk_client_detail_client")
)
@NamedStoredProcedureQuery(
	name = "Client.buy_greater_than_avg",
	procedureName = "buy_greater_than_avg",
	resultClasses = { Client.class },
	parameters = {
		@StoredProcedureParameter(name = "year", type = Integer.class, mode = ParameterMode.IN)
	}
)
public class Client extends BaseEntityId {
	@NotBlank
	@Column(length = 100, nullable = false)
	private String name;

	@Transient
	private String firstName;

	@NotBlank
	@Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$")
	@Column(length = 14, nullable = false)
	private String cpf;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(table = "clients_details", nullable = false)
	private ClientSex sex;

	@Column(table = "clients_details", name = "birth_date")
	private LocalDate birthDate;

	@OneToMany(mappedBy = "client")
	private List<Order> orders;

	@NotEmpty
	@ElementCollection
	@CollectionTable(
		name = "clients_contacts",
		joinColumns = @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_client_contact"))
	)
	@MapKeyColumn(name = "type") // name to column represented by first type of map (key)
	@Column(name = "description") // name to column represented by second type of map (value)
	private Map<String, String> contacts;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Client other = (Client) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(sex, other.sex)
			&& Objects.equals(birthDate, other.birthDate)
			&& Objects.equals(cpf, other.cpf);
	}
	
	@PostLoad
	public void postLoad() {
		Optional.ofNullable(name)
			.filter(name -> !name.isBlank())
			.ifPresent(name -> {
				this.firstName = name.split(" ")[0];
			});
	}
}
