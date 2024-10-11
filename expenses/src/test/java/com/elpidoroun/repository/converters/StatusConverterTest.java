package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.Status;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StatusConverterTest {

    private final StatusConverter converter = new StatusConverter();

    @Test
    public void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(Status.ACTIVE)).isEqualTo("active");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("active")).isEqualTo(Status.ACTIVE);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute_InvalidValue() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute("invalid"))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("No Status found for value: invalid");
    }
}