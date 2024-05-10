package com.elpidoroun.financialportfolio.security.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")

    private String fullName;

    @Column(name = "email")

    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    protected User(){}

    private User(Long id, String fullName, String email, String password, Role role){
        this.id = id;
        this.fullName = requireNonNull(fullName, "fullname is missing");
        this.email = requireNonNull(email, "email is missing");
        this.password = requireNonNull(password, "password is missing");
        this.role = requireNonNull(role, "role is missing");
    }

    public Long getId() { return id; }

    public String getFullName() { return fullName; }

    public String getEmail() { return email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() { return password; }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Builder builder(){ return new Builder(); }

    public static class Builder {
        private Long id;
        private String fullName;
        private String email;
        private String password;
        private Role role;

        private Builder(){}

        public Builder withFullName(String fullName){
            this.fullName = fullName;
            return this;
        }

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return this;
        }

        public Builder withRole(Role role){
            this.role = role;
            return this;
        }

        public User build(){
            return new User(id, fullName, email, password, role);
        }
    }
}