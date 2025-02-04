package com.algaworks.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class OrderAddress {
	@NotBlank
	@Pattern(regexp = "^\\d{5}-\\d{3}$")
	@Column(name = "zip_code", length = 9)
	private String zipCode;

	@NotBlank
	@Column(length = 100)
	private String address;

	@NotBlank
	@Column(length = 10)
	private String number;
	
	@Column(length = 50)
	private String complement;

	@NotBlank
	@Column(length = 50)
	private String neighborhood;

	@NotBlank
	@Column(length = 50)
	private String city;

	@NotBlank
	@Size(min = 2, max = 2)
	@Column(length = 2)
	private String state;
}
