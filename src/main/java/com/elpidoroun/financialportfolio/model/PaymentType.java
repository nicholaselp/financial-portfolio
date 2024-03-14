package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

import static java.util.Objects.requireNonNull;

public enum PaymentType implements EnumStringValue {

    MONTHLY("MONTHLY"),
    YEARLY("YEARLY");
    private final String paymentType;
    private PaymentType(String paymentType){
        this.paymentType = requireNonNull(paymentType, "paymentType is missing");
    }

    @Override
    public String getValue() {
        return paymentType;
    }
}
