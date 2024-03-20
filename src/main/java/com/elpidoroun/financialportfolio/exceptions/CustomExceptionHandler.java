package com.elpidoroun.financialportfolio.exceptions;


import com.elpidoroun.financialportfolio.controller.MainController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static String INCORRECT_REQUEST = "Error in request";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new MainController.ErrorResponse(extractErrorMessage(ex), INCORRECT_REQUEST));
    }

    private String extractErrorMessage(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();

        int unexpectedValueIndex = message.indexOf("Unexpected value");
        int notValidRepresentationIndex = message.indexOf("not a valid representation");

        if (unexpectedValueIndex != -1) {
            return message.substring(unexpectedValueIndex);
        } else if (notValidRepresentationIndex != -1) {
            int startIndex = message.lastIndexOf("String");
            return message.substring(startIndex).trim();
        } else {
            return "Error occurred";
        }
    }
}