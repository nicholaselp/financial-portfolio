package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;

public enum ExpenseType implements EnumStringValue {

    FIXED("fixed"),
    NOT_FIXED("not-fixed");

    private String value;

    ExpenseType(String value){ this.value = requireNonBlank(value, "ExpenseType is missing"); }

    @Override
    public String getValue() { return value; }
}
