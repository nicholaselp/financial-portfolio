package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

import static java.util.Objects.requireNonNull;

public enum Currency implements EnumStringValue {

    EURO("euro"),
    USD("USD");
    private final String currency;

    private Currency(String currency){
        this.currency = requireNonNull(currency, "Currency is missing");
    }

    @Override
    public String getValue() {
        return currency;
    }
}