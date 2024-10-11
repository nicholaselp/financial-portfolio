package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.ImportRequestStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Converter(autoApply = true)
public class ImportRequestLineStatusConverter implements AttributeConverter<ImportRequestStatus, String> {
    @Override
    public String convertToDatabaseColumn(ImportRequestStatus importRequestStatus) {
        return nonNull(importRequestStatus) ? importRequestStatus.getValue() : null;
    }

    @Override
    public ImportRequestStatus convertToEntityAttribute(String dbStatus) {
        if(nonNull(dbStatus)){
         return Arrays.stream(ImportRequestStatus.values())
                 .filter(importRequestStatus -> importRequestStatus.getValue().equals(dbStatus))
                 .findFirst()
                 .orElseThrow(() -> new DatabaseOperationException("No ImportRequestStatus found for value: " + dbStatus));
        }
        return null;
    }
}