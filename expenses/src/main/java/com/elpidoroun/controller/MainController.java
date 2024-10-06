package com.elpidoroun.controller;


import com.elpidoroun.controller.command.AbstractRequest;
import com.elpidoroun.controller.command.Command;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;


public class MainController {
    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request){
        return execute(command, request, ResponseEntity::ok);
    }

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request,
            Function<ResponseT, ResponseEntity<?>> responseBuilder) {

        MDC.put("operation", command.getOperation());

        if (command.isRequestIncomplete(request)) {
            throw new IllegalArgumentException(command.missingParams(request));
        }

        return responseBuilder.apply(command.execute(request));
    }
}