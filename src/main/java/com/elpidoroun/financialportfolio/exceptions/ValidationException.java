package com.elpidoroun.financialportfolio.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{

    private final String errorType;

    public ValidationException(String message){
        super(message);
        this.errorType = "Validation error occurred";
    }
}
