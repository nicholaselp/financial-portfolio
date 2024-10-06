package com.elpidoroun.mappers;


import com.elpidoroun.model.Currency;
import com.elpidoroun.generated.dto.CurrencyDto;

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