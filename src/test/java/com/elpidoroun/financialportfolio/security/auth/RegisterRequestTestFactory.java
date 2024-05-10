package com.elpidoroun.financialportfolio.security.auth;

import com.elpidoroun.financialportfolio.security.user.Role;

public class RegisterRequestTestFactory {

    public static RegisterRequest createReqisterRequestForUser(){
        return new RegisterRequest("Name", "email", "password", Role.USER);
    }

    public static RegisterRequest createRegisterRequestForAdmin(){
        return new RegisterRequest("Name", "email", "password", Role.ADMIN);
    }
}