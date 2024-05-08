package com.elpidoroun.financialportfolio.exceptions;


import com.elpidoroun.financialportfolio.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static String INCORRECT_REQUEST = "Error in request";
    private final static String ACCESS_DENIED = "Access Denied";
    private final static String FORBIDDEN_MESSAGE = "Forbidden";
    private final static String GENERIC_MESSAGE = "error";


    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        logger.error(ex.getMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new MainController.ErrorResponse("Exception occurred", GENERIC_MESSAGE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new MainController.ErrorResponse(extractErrorMessage(ex), INCORRECT_REQUEST));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Object> handleInsufficientAuthenticationException(Exception ex) {
        logger.error(ex.getMessage());
        String errorMessage = "Unauthorized access. No authentication provided for the resource";
        return ResponseEntity.status(UNAUTHORIZED)
                .body(new MainController.ErrorResponse(errorMessage, ACCESS_DENIED));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        logger.error(ex.getMessage());
        String errorMessage = "Unauthorized access.";
        return ResponseEntity.status(UNAUTHORIZED)
                .body(new MainController.ErrorResponse(errorMessage, FORBIDDEN_MESSAGE));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(FORBIDDEN)
                .body(new MainController.ErrorResponse("You do not have Permission to access this API", FORBIDDEN_MESSAGE));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(UNAUTHORIZED)
                .body(new MainController.ErrorResponse(ex.getMessage(), ACCESS_DENIED));
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