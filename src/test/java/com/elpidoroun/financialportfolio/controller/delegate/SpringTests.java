package com.elpidoroun.financialportfolio.controller.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public abstract class SpringTests {

    @Autowired private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    public <T> T extractResponse(MvcResult result, Class<T> clazz) throws Exception {
        result.getResponse().getContentAsString();
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    public MockMvc mockMvc() { return mockMvc; }
}