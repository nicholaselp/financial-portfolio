package com.elpidoroun.financialportfolio.exceptions;

import lombok.Getter;

@Getter
public class DatabaseOperationException extends RuntimeException{

    private final String errorType;

    public DatabaseOperationException(String message){
        super(message);
        this.errorType = "Database error";
    }
}