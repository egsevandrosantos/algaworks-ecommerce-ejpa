package com.algaworks.ecommerce.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanYesNoConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return Boolean.TRUE.equals(attribute) ? "YES" : "NO";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "YES".equalsIgnoreCase(dbData);
    }
}
