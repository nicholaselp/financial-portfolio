package com.elpidoroun.financialportfolio.controller;


import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.elpidoroun.financialportfolio.exceptions.DatabaseOperationException;
import com.elpidoroun.financialportfolio.exceptions.ExpenseNotFoundException;
import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final String INCOMPLETE_REQUEST = "Incomplete request";

    private static final Map<Class<?>, HttpStatus> exceptionStatuses = new ImmutableMap.Builder<Class<?>, HttpStatus>()
            .put(ValidationException.class, BAD_REQUEST)
            .put(ExpenseNotFoundException.class, CONFLICT)
            .put(IllegalArgumentException.class, BAD_REQUEST)
            .put(DatabaseOperationException.class, BAD_REQUEST)
            .build();

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request){
        return execute(command, request, ResponseEntity::ok);
    }

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request,
            Function<ResponseT, ResponseEntity<?>> responseBuilder){
        try{

            MDC.put("operation", command.getOperation());

            logger.info("somethingggggg");

            if(command.isRequestIncomplete(request)){
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ErrorResponse(command.missingParams(request), INCOMPLETE_REQUEST));
            }

            return responseBuilder.apply(command.execute(request));
        } catch (Exception exception){
            logger.error(exception.getMessage());

            return ResponseEntity.status(httpStatusByException(exception))
                    .body(new ErrorResponse(exception.getMessage(), getErrorType(exception)));
        }
    }

    private String getErrorType(Exception exception) {
        try {
            return (String) exception.getClass().getField("errorType").get(exception);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "Unknown error type";
        }
    }

    private HttpStatus httpStatusByException(Exception exception){
        return Optional.ofNullable(exceptionStatuses.get(exception.getClass())).orElse(INTERNAL_SERVER_ERROR);
    }

    private record ErrorResponse(String message, String errorType) {
            private ErrorResponse(String message, String errorType) {
                this.message = requireNonNull(message, "Message is missing");
                this.errorType = requireNonNull(errorType, "ErrorType is missing");
            }
        }
}