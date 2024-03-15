package com.elpidoroun.financialportfolio.repository.converters;

import com.elpidoroun.financialportfolio.model.Currency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.nonNull;

/**This converter is needed to store and retrieve Enum values from DB records**/
@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {
    @Override
    public String convertToDatabaseColumn(Currency currency) {
        return nonNull(currency) ? currency.getValue() : null;
    }

    @Override
    public Currency convertToEntityAttribute(String dbCurrency) {
        return nonNull(dbCurrency) ? Currency.valueOf(dbCurrency.toUpperCase()) : null;
    }
}