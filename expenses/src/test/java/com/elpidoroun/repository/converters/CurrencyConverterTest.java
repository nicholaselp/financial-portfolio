package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.Currency;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CurrencyConverterTest {

    private final CurrencyConverter converter = new CurrencyConverter();

    @Test
    public void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(Currency.USD)).isEqualTo("USD");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("USD")).isEqualTo(Currency.USD);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute_InvalidValue() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute("invalid"))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("No Currency found for value: invalid");
    }
}