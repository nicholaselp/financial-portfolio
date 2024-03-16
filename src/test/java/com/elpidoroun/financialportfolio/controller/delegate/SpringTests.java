package com.elpidoroun.financialportfolio.controller.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

public abstract class SpringTests {

    @Autowired
    protected ObjectMapper objectMapper;

    public <T> T extractResponse(MvcResult result, Class<T> clazz) throws Exception {
        result.getResponse().getContentAsString();
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }
}