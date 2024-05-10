package com.elpidoroun.financialportfolio.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_CREATE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_DELETE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_READ;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CATEGORY_UPDATE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_CREATE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_DELETE;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_READ;
import static com.elpidoroun.financialportfolio.security.user.Permissions.EXPENSE_UPDATE;


@RequiredArgsConstructor
public enum Role {
    USER(Set.of(EXPENSE_CREATE,
            EXPENSE_UPDATE,
            EXPENSE_READ,
            EXPENSE_DELETE,
            EXPENSE_CATEGORY_READ)),
    ADMIN(Set.of(EXPENSE_CATEGORY_CREATE,
            EXPENSE_CATEGORY_UPDATE,
            EXPENSE_CATEGORY_READ,
            EXPENSE_CATEGORY_DELETE,
            EXPENSE_CREATE,
            EXPENSE_UPDATE,
            EXPENSE_READ,
            EXPENSE_DELETE));

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }
}