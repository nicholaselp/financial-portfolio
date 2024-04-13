package com.elpidoroun.financialportfolio.exceptions;

public class EntityNotFoundException extends RuntimeException{

    private final String errorType;

    public EntityNotFoundException(String message){
        super(message);
        this.errorType = "Entity Not Found";
    }
}