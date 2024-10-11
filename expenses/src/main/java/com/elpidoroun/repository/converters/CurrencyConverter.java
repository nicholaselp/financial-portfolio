package com.elpidoroun.repository.converters;

import com.elpidoroun.exception.DatabaseOperationException;
import com.elpidoroun.model.Currency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Objects;

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
        if(nonNull(dbCurrency)){
            return Arrays.stream(Currency.values())
                    .filter(Objects::nonNull)
                    .filter(currency -> currency.getValue().equals(dbCurrency))
                    .findFirst().orElseThrow(() -> new DatabaseOperationException("No Currency found for value: " + dbCurrency));
        }

        return null;
    }
}