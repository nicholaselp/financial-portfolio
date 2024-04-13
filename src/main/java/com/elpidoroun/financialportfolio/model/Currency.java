package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;

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