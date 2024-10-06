package com.elpidoroun.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{

    private final String errorType;

    public EntityNotFoundException(String message){
        super(message);
        this.errorType = "Entity Not Found";
    }
}