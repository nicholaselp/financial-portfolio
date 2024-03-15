package com.elpidoroun.financialportfolio.exceptions;

public class ExpenseNotFoundException extends RuntimeException{

    private final String errorType;

    public ExpenseNotFoundException(String message){
        super(message);
        this.errorType = "Expense Not Found";
    }
}