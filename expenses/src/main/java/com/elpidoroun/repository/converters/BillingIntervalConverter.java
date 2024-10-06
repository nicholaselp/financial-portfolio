package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.BillingInterval;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**This converter is needed to store and retrieve Enum values from DB records**/
@Converter(autoApply = true)
public class BillingIntervalConverter implements AttributeConverter<BillingInterval, String> {

    @Override
    public String convertToDatabaseColumn(BillingInterval billingInterval) {
        return nonNull(billingInterval) ? billingInterval.getValue() : null;
    }

    @Override
    public BillingInterval convertToEntityAttribute(String dbBillingInterval) {
        if(nonNull(dbBillingInterval)){
            return Arrays.stream(BillingInterval.values())
                    .filter(Objects::nonNull)
                    .filter(paymentType -> paymentType.getValue().equals(dbBillingInterval))
                    .findFirst().orElseThrow(() -> new DatabaseOperationException("No BillingInterval found for value: " + dbBillingInterval));
        }

        return null;
    }
}