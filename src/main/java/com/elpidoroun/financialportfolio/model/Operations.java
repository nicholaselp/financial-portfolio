package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.utilities.EnumStringValue;

public enum Operations implements EnumStringValue {

    CREATE_EXPENSE("create-expense"),
    GET_EXPENSE_BY_ID("get-expense-by-id"),
    UPDATE_EXPENSE("update-expense"),
    DELETE_EXPENSE_BY_ID("delete-expense-by-id");
    private String operation;

    private Operations(String operation){
        this.operation = operation;
    }

    @Override
    public String getValue() {
        return operation;
    }
}
