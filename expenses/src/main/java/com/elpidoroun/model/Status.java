package com.elpidoroun.model;

import com.elpidoroun.utilities.EnumStringValue;

import static com.elpidoroun.utilities.StringUtils.requireNonBlank;

public enum Status implements EnumStringValue {

    ACTIVE("active"),
    DELETED("deleted");
    private final String status;

    Status(String status) {
        this.status = requireNonBlank(status, "Status is missing");
    }

    @Override
    public String getValue() {
        return status;
    }
}