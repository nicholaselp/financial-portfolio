package com.elpidoroun.financialportfolio.exceptions;

public class DatabaseOperationException extends RuntimeException{

    private final String errorType;

    public DatabaseOperationException(String message){
        super(message);
        this.errorType = "Expense Not Found";
    }
}