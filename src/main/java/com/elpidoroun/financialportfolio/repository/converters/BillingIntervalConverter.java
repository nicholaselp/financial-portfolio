package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.model.BillingInterval;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.nonNull;

/**This converter is needed to store and retrieve Enum values from DB records**/
@Converter(autoApply = true)
public class BillingIntervalConverter implements AttributeConverter<BillingInterval, String> {

    @Override
    public String convertToDatabaseColumn(BillingInterval billingInterval) {
        return nonNull(billingInterval) ? billingInterval.getValue() : null;
    }

    @Override
    public BillingInterval convertToEntityAttribute(String dbPaymentType) {
        return nonNull(dbPaymentType) ? BillingInterval.valueOf(dbPaymentType.toUpperCase()) : null;
    }
}