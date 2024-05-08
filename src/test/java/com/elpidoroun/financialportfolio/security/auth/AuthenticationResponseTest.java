package com.elpidoroun.financialportfolio.security.auth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationResponseTest {

    @Test
    public void testEqualsAndHashCode() {
        AuthenticationResponse response1 = AuthenticationResponse.builder()
                .token("token123")
                .error("error message")
                .build();

        AuthenticationResponse response2 = AuthenticationResponse.builder()
                .token("token123")
                .error("error message")
                .build();

        assertThat(response1).isEqualTo(response2);
        assertThat(response2).isEqualTo(response1);

        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());

        response2.setToken("newToken");

        assertThat(response1).isNotEqualTo(response2);
        assertThat(response2).isNotEqualTo(response1);

        assertThat(response1.hashCode()).isNotEqualTo(response2.hashCode());
    }
}