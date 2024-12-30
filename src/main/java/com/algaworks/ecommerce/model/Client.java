package com.algaworks.ecommerce.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.algaworks.ecommerce.listener.LoggingLoadedEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "clients")
@EntityListeners(value = { LoggingLoadedEntityListener.class })
public class Client {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	@Transient
	private String firstName;
	@Enumerated(EnumType.STRING)
	private ClientSex sex;
	@OneToMany(mappedBy = "client")
	private List<Order> orders;
	
	public boolean fullEquals(Object obj) {
		if (!this.equals(obj)) return false;
		
		Client other = (Client) obj;
		return Objects.equals(name, other.name)
			&& Objects.equals(sex, other.sex);
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
