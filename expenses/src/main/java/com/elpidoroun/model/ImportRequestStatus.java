package com.elpidoroun.model;

import com.elpidoroun.utilities.EnumStringValue;

import static com.elpidoroun.utilities.StringUtils.requireNonBlank;

public enum ImportRequestStatus implements EnumStringValue {

    PENDING("pending"),
    SUCCESS("success"),
    FAILED("failed"),
    PARTIAL_SUCCESS("partial-success");

    private final String importRequestStatus;

    ImportRequestStatus(String status) {
        this.importRequestStatus = requireNonBlank(status, "ImportRequestStatus is missing");
    }

    @Override
    public String getValue() {
        return importRequestStatus;
    }
}