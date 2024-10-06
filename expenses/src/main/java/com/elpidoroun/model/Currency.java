package com.elpidoroun.model;

import com.elpidoroun.utilities.EnumStringValue;

import static com.elpidoroun.utilities.StringUtils.requireNonBlank;

public enum Currency implements EnumStringValue {

    EURO("EUR"),
    USD("USD");
    private final String currency;

    Currency(String currency){
        this.currency = requireNonBlank(currency, "Currency is missing");
    }

    @Override
    public String getValue() {
        return currency;
    }
}