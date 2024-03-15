package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.model.PaymentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.nonNull;

/**This converter is needed to store and retrieve Enum values from DB records**/
@Converter(autoApply = true)
public class PaymentTypeConverter implements AttributeConverter<PaymentType, String> {
    @Override
    public String convertToDatabaseColumn(PaymentType paymentType) {
        return nonNull(paymentType) ? paymentType.getValue() : null;
    }

    @Override
    public PaymentType convertToEntityAttribute(String dbPaymentType) {
        return nonNull(dbPaymentType) ? PaymentType.valueOf(dbPaymentType.toUpperCase()) : null;
    }
}