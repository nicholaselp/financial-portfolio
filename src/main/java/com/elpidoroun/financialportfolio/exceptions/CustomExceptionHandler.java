package com.elpidoroun.financialportfolio.exceptions;

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

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static String INCORRECT_REQUEST = "Error in request";
    private final static String ACCESS_DENIED = "Access Denied";
    private final static String FORBIDDEN_MESSAGE = "Forbidden";
    private final static String GENERIC_MESSAGE = "Generic Exception occurred";

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        logger.error("Exception occurred", exception);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(exception.getMessage(), GENERIC_MESSAGE));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException exception){
        logger.error("Validation Exception occurred", exception);

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorType()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception){
        logger.error("EntityNotFoundException Exception occurred", exception);

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorType()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception){
        logger.error("IllegalArgumentException Exception occurred", exception);

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorType()));
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<Object> handleDatabaseOperationException(DatabaseOperationException exception){
        logger.error("DatabaseOperationException Exception occurred", exception);

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorType()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        logger.error("HttpMessageNotReadableException occurred", exception);

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(extractErrorMessage(exception), INCORRECT_REQUEST));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Object> handleInsufficientAuthenticationException(Exception exception) {
        logger.error("InsufficientAuthenticationException occurred", exception);

        String errorMessage = "Unauthorized access. No authentication provided for the resource";
        return ResponseEntity.status(UNAUTHORIZED)
                .body(new ErrorResponse(errorMessage, ACCESS_DENIED));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException exception) {
        logger.error("UnauthorizedException occurred", exception);

        String errorMessage = "Unauthorized access.";
        return ResponseEntity.status(UNAUTHORIZED)
                .body(new ErrorResponse(errorMessage, FORBIDDEN_MESSAGE));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        logger.error("AccessDeniedException occurred", exception);

        return ResponseEntity.status(FORBIDDEN)
                .body(new ErrorResponse("You do not have Permission to access this API", FORBIDDEN_MESSAGE));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        logger.error("BadCredentialsException occurred", exception);

        return ResponseEntity.status(UNAUTHORIZED)
                .body(new ErrorResponse(exception.getMessage(), ACCESS_DENIED));
    }


    private String extractErrorMessage(HttpMessageNotReadableException exception) {
        String message = exception.getMessage();

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

    public record ErrorResponse(String message, String errorType) {

        public String getMessage(){ return message; }
        public String getErrorType(){ return errorType; }
        public ErrorResponse(String message, String errorType) {
            this.message = requireNonBlank(message, "Message is missing");
            this.errorType = requireNonBlank(errorType, "ErrorType is missing");
        }
    }
}