package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**This converter is needed to store and retrieve Enum values from DB records**/
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        return nonNull(status) ? status.getValue() : null;
    }

    @Override
    public Status convertToEntityAttribute(String dbStatus) {
        if(nonNull(dbStatus)){
            return Arrays.stream(Status.values())
                    .filter(Objects::nonNull)
                    .filter(status -> status.getValue().equals(dbStatus))
                    .findFirst().orElseThrow(() -> new DatabaseOperationException("No Status found for value: " + dbStatus));
        }
        return null;
    }
}