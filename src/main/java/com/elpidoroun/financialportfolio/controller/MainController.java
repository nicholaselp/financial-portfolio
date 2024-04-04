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

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.elpidoroun.financialportfolio.utilities.StringUtils.requireNonBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

            if(command.isRequestIncomplete(request)){
                return ResponseEntity.status(BAD_REQUEST)
                        .body(new ErrorResponse(command.missingParams(request), INCOMPLETE_REQUEST));
            }

            return responseBuilder.apply(command.execute(request));
        } catch (Exception exception){
            logger.error(exception.getMessage(), exception);

            return ResponseEntity.status(httpStatusByException(exception))
                    .body(new ErrorResponse(exception.getMessage(), getErrorType(exception)));
        }
    }

    private String getErrorType(Exception exception) {
        try {
            Field errorTypeField = exception.getClass().getDeclaredField("errorType");
            errorTypeField.setAccessible(true);
            return (String) errorTypeField.get(exception);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "Unknown error type";
        }
    }

    private HttpStatus httpStatusByException(Exception exception){
        return Optional.ofNullable(exceptionStatuses.get(exception.getClass())).orElse(INTERNAL_SERVER_ERROR);
    }

    public record ErrorResponse(String message, String errorType) {
            public ErrorResponse(String message, String errorType) {
                this.message = requireNonBlank(message, "Message is missing");
                this.errorType = requireNonBlank(errorType, "ErrorType is missing");
            }
        }
}