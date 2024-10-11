package com.elpidoroun.model;

import com.elpidoroun.utilities.EnumStringValue;

import static com.elpidoroun.utilities.StringUtils.requireNonBlank;

public enum ExpenseType implements EnumStringValue {

    FIXED("fixed"),
    NOT_FIXED("not-fixed");

    private final String value;

    ExpenseType(String value){ this.value = requireNonBlank(value, "ExpenseType is missing"); }

    @Override
    public String getValue() { return value; }
}
