package com.elpidoroun.financialportfolio.security.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_CREATE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_DELETE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_READ;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_UPDATE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CREATE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_DELETE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_READ;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_UPDATE;
import static org.assertj.core.api.Assertions.assertThat;

public class RoleTest {

    @Test
    public void testUserAuthorities() {
    Role userRole = Role.USER;

    List<SimpleGrantedAuthority> authorities = userRole.getAuthorities();

    assertThat(authorities).hasSize(5);
    assertThat(authorities).extracting("authority")
            .containsExactlyInAnyOrder(
                    EXPENSE_CREATE.getValue(),
                    EXPENSE_UPDATE.getValue(),
                    EXPENSE_READ.getValue(),
                    EXPENSE_DELETE.getValue(),
                    EXPENSE_CATEGORY_READ.getValue()
            );
}

    @Test
    public void testAdminAuthorities() {
        Role adminRole = Role.ADMIN;

        List<SimpleGrantedAuthority> authorities = adminRole.getAuthorities();

        assertThat(authorities).hasSize(8);
        assertThat(authorities).extracting("authority")
                .containsExactlyInAnyOrder(
                        EXPENSE_CATEGORY_CREATE.getValue(),
                        EXPENSE_CATEGORY_UPDATE.getValue(),
                        EXPENSE_CATEGORY_READ.getValue(),
                        EXPENSE_CATEGORY_DELETE.getValue(),
                        EXPENSE_CREATE.getValue(),
                        EXPENSE_UPDATE.getValue(),
                        EXPENSE_READ.getValue(),
                        EXPENSE_DELETE.getValue()
                );
    }
}