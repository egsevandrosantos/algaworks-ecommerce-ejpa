package com.algaworks.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.algaworks.ecommerce.listener.LoggingLoadedEntityListener;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "clients")
@EntityListeners(value = { LoggingLoadedEntityListener.class })
@SecondaryTable(name = "clients_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"))
public class Client extends BaseEntityId {
	private String name;
	@Transient
	private String firstName;
	@Enumerated(EnumType.STRING)
	@Column(table = "clients_details")
	private ClientSex sex;
	@Column(table = "clients_details", name = "birth_date")
	private LocalDate birthDate;
	@OneToMany(mappedBy = "client")
	private List<Order> orders;
	@ElementCollection
	@CollectionTable(
		name = "clients_contacts",
		joinColumns = @JoinColumn(name = "client_id")
	)
	@MapKeyColumn(name = "type") // name to column represented by first type of map (key)
	@Column(name = "description") // name to column represented by second type of map (value)
	private Map<String, String> contacts;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Client other = (Client) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(sex, other.sex)
			&& Objects.equals(birthDate, other.birthDate);
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
