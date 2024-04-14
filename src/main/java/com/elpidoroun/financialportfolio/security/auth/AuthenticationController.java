package com.elpidoroun.financialportfolio.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.elpidoroun.financialportfolio.security.auth.AuthenticationController.AUTH_URL;

@RestController
@RequestMapping(AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    public static final String AUTH_URL = "/v1/auth";
    public final static String REGISTER_URL = "/register";
    public final static String AUTHENTICATE_URL = "/authenticate";

    @PostMapping(REGISTER_URL)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping(AUTHENTICATE_URL)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);

    }
}
