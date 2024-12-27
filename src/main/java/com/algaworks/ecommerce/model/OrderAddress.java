package com.algaworks.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class OrderAddress {
	@Column(name = "zip_code")
	private String zipCode;
	private String address;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String state;
}
