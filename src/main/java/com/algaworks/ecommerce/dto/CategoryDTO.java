package com.algaworks.ecommerce.dto;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
}
