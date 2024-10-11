package com.elpidoroun.security.user;

import com.elpidoroun.utilities.EnumStringValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions implements EnumStringValue {

    EXPENSE_CATEGORY_CREATE("expense-category:create"),
    EXPENSE_CATEGORY_UPDATE("expense-category:update"),
    EXPENSE_CATEGORY_READ("expense-category:read"),
    EXPENSE_CATEGORY_DELETE("expense-category:delete"),
    EXPENSE_CREATE("expense:create"),
    EXPENSE_UPDATE("expense:update"),
    EXPENSE_READ("expense:read"),
    EXPENSE_DELETE("expense:delete");

    private final String permission;

    @Override
    public String getValue() { return permission; }
}
