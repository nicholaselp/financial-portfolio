package com.elpidoroun.financialportfolio.mappers;


import com.elpidoroun.financialportfolio.model.Currency;
import com.elpidoroun.financialportfolio.generated.dto.CurrencyDto;

public class CurrencyMapper {

    public static CurrencyDto toDto(Currency currency){
        return switch (currency) {
            case EURO -> CurrencyDto.EURO;
            case USD -> CurrencyDto.USD;
        };
    }

    public static Currency toDomain(CurrencyDto currencyDto){
        return switch (currencyDto) {
            case EURO -> Currency.EURO;
            case USD -> Currency.USD;
        };
    }

}