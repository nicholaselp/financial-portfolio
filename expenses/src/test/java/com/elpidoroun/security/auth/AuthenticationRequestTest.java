package com.elpidoroun.security.auth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationRequestTest {

    @Test
    public void testEqualsAndHashCode() {
        AuthenticationRequest request1 = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        AuthenticationRequest request2 = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        assertThat(request1).isEqualTo(request2);
        assertThat(request2).isEqualTo(request1);

        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        request2.setEmail("newemail@example.com");

        assertThat(request1).isNotEqualTo(request2);
        assertThat(request2).isNotEqualTo(request1);

        assertThat(request1.hashCode()).isNotEqualTo(request2.hashCode());
    }
}