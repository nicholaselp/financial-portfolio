package com.elpidoroun.security.auth;

import com.elpidoroun.security.auth.RegisterRequest;
import com.elpidoroun.security.user.Role;

public class RegisterRequestTestFactory {

    public static RegisterRequest createReqisterRequestForUser(){
        return new RegisterRequest("Name", "email", "password", Role.USER);
    }

    public static RegisterRequest createRegisterRequestForAdmin(){
        return new RegisterRequest("Name", "email", "password", Role.ADMIN);
    }
}