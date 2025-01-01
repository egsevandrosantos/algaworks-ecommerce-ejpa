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
	@Column(name = "zip_code", length = 9)
	private String zipCode;
	
	@Column(length = 100)
	private String address;
	
	@Column(length = 10)
	private String number;
	
	@Column(length = 50)
	private String complement;
	
	@Column(length = 50)
	private String neighborhood;
	
	@Column(length = 50)
	private String city;
	
	@Column(length = 2)
	private String state;
}
