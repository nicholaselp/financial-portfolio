package com.elpidoroun.financialportfolio.security.auth;

import com.elpidoroun.financialportfolio.controller.delegate.SpringTestsHelper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.elpidoroun.financialportfolio.security.auth.AuthenticationController.AUTHENTICATE_URL;
import static com.elpidoroun.financialportfolio.security.auth.AuthenticationController.AUTH_URL;
import static com.elpidoroun.financialportfolio.security.auth.AuthenticationController.REGISTER_URL;
import static com.elpidoroun.financialportfolio.security.auth.RegisterRequestTestFactory.createReqisterRequestForUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthenticationControllerTest extends SpringTestsHelper {

    @Test
    public void success_register_user() throws Exception {
        MvcResult result = mockMvc().perform(post(AUTH_URL + REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(createReqisterRequestForUser())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = extractResponse(result, AuthenticationResponse.class);
        assertThat(response.getToken()).isNotEmpty();
        assertThat(response.getError()).isNull();
    }


    @Test
    public void fail_register_user_already_exists() throws Exception {
        var request = RegisterRequest.builder()
                .email("test_admin@gmail.com")
                .fullName("test admin")
                .password("test password")
                .build();

        mockMvc().perform(post(AUTH_URL + REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void success_authenticate() throws Exception {
        var request = AuthenticationRequest.builder()
                .email("test_admin@gmail.com")
                .password("1234")
                .build();

        MvcResult result = mockMvc().perform(post(AUTH_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = extractResponse(result, AuthenticationResponse.class);
        assertThat(response.getToken()).isNotEmpty();
        assertThat(response.getError()).isNull();
    }


    @Test
    //incorrect password so it fails
    public void fail_authentication() throws Exception {
        var request = AuthenticationRequest.builder()
                .email("test_admin@gmail.com")
                .password("password")
                .build();

        mockMvc().perform(post(AUTH_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

}
