package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;

public enum ImportRequestStatus implements EnumStringValue {

    PENDING("pending"),
    SUCCESS("success"),
    FAILED("failed");

    private final String importRequestStatus;

    ImportRequestStatus(String status) {
        this.importRequestStatus = requireNonBlank(status, "ImportRequestStatus is missing");
    }

    @Override
    public String getValue() {
        return importRequestStatus;
    }
}