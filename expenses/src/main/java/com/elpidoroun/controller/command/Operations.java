package com.elpidoroun.controller.command;


import com.elpidoroun.utilities.EnumStringValue;

public enum Operations implements EnumStringValue {

    CREATE_EXPENSE("create-expense"),
    GET_EXPENSE_BY_ID("get-expense-by-id"),
    GET_EXPENSES("get-expenses"),
    UPDATE_EXPENSE("update-expense"),
    DELETE_EXPENSE_BY_ID("delete-expense-by-id"),
    IMPORT_EXPENSES("import-expenses"),
    GET_IMPORT_REQUEST("get-import-request"),

    CREATE_EXPENSE_CATEGORY("create-expense-category"),
    GET_EXPENSE_CATEGORY_BY_ID("get-expense-category-by-id"),
    GET_EXPENSE_CATEGORIES("get-expense-categories"),
    UPDATE_EXPENSE_CATEGORY("update-expense-category"),
    DELETE_EXPENSE_CATREGORY_BY_ID("delete-expense-category-by-id");

    private final String operation;

    Operations(String operation){
        this.operation = operation;
    }

    @Override
    public String getValue() {
        return operation;
    }
}
