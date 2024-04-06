package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.model.BillingInterval;
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
    public BillingInterval convertToEntityAttribute(String dbPaymentType) {
        if(nonNull(dbPaymentType)){
            return Arrays.stream(BillingInterval.values())
                    .filter(Objects::nonNull)
                    .filter(paymentType -> paymentType.getValue().equals(dbPaymentType))
                    .findFirst().orElseThrow(() -> new DatabaseOperationException("No PaymentType found for value: " + dbPaymentType));
        }

        return null;
    }
}