package com.elpidoroun.financialportfolio.converters;


import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.model.Currency;
import com.elpidoroun.financialportfolio.generated.dto.CurrencyDto;

public class CurrencyConverter {

    public static CurrencyDto toDto(Currency currency){
        return switch (currency) {
            case EURO -> CurrencyDto.EURO;
            case USD -> CurrencyDto.USD;
            default -> throw new IllegalArgumentException("Currency: " + currency + " not supported.");
        };
    }

    public static Currency toDomain(CurrencyDto currencyDto){
        return switch (currencyDto) {
            case EURO -> Currency.EURO;
            case USD -> Currency.USD;
            default -> throw new IllegalArgumentException("CurrencyDto " + currencyDto + " not supported");
        };
    }

}