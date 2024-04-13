package com.elpidoroun.financialportfolio.service.validation;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

public enum ErrorType implements EnumStringValue {
    SYSTEM("system error");

    private final String errorType;

    ErrorType(String errorType) {
        this.errorType = errorType;
    }

    @Override
    public String getValue() {
        return errorType;
    }
}
