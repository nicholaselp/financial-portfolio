package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;

public enum BillingInterval implements EnumStringValue {

    MONTHLY("monthly"),
    BI_MONTHLY("bi-monthly"),
    QUARTERLY("quarterly"),
    YEARLY("yearly");

    private final String billingInterval;

    BillingInterval(String billingInterval){ this.billingInterval = requireNonBlank(billingInterval, "billingInterval is missing"); }

    @Override
    public String getValue() {
        return billingInterval;
    }
}
