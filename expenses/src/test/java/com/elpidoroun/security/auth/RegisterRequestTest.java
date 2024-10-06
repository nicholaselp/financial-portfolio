package com.elpidoroun.security.auth;

import com.elpidoroun.security.user.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterRequestTest {

    @Test
    public void testEqualsAndHashCode() {
        RegisterRequest request1 = RegisterRequest.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .password("password123")
                .role(Role.USER)
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .password("password123")
                .role(Role.USER)
                .build();

        assertThat(request1).isEqualTo(request2);
        assertThat(request2).isEqualTo(request1);

        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        request2.setFullName("Jane Doe");

        assertThat(request1).isNotEqualTo(request2);
        assertThat(request2).isNotEqualTo(request1);

        assertThat(request1.hashCode()).isNotEqualTo(request2.hashCode());
    }
}