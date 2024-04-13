package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.model.BillingInterval;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BillingIntervalConverterTest {

    private final BillingIntervalConverter converter = new BillingIntervalConverter();


    @Test
    public void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(BillingInterval.MONTHLY)).isEqualTo("monthly");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("monthly")).isEqualTo(BillingInterval.MONTHLY);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void convertToEntityAttribute_InvalidValue() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute("invalid"))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("No BillingInterval found for value: invalid");
    }
}