package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.model.ExpenseType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExpenseTypeConverterTest {

    private final ExpenseTypeConverter converter = new ExpenseTypeConverter();

    @Test
    public void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(ExpenseType.FIXED)).isEqualTo("fixed");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("fixed")).isEqualTo(ExpenseType.FIXED);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute_InvalidValue() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute("invalid"))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("No ExpenseType found for value: invalid");
    }
}