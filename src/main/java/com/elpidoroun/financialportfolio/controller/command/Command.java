package com.elpidoroun.financialportfolio.controller.command;

public interface Command<RequestT extends AbstractRequest, ResponseT> {
        ResponseT execute(RequestT request);


}