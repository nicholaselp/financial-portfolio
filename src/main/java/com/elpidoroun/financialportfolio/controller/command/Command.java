package com.elpidoroun.financialportfolio.controller.command;

import java.util.Optional;

public interface Command<RequestT extends AbstractRequest, ResponseT> {
        ResponseT execute(RequestT request);
        boolean isRequestIncomplete(RequestT request);
        String missingParams(RequestT request);
        String getOperation();

}