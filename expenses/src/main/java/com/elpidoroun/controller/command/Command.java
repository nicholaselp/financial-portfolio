package com.elpidoroun.controller.command;

public interface Command<RequestT extends AbstractRequest, ResponseT> {
        ResponseT execute(RequestT request);
        boolean isRequestIncomplete(RequestT request);
        String missingParams(RequestT request);
        String getOperation();

}