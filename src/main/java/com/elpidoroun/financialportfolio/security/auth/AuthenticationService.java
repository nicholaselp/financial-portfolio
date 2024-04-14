package com.elpidoroun.financialportfolio.security.auth;

import com.elpidoroun.financialportfolio.security.config.JwtService;
import com.elpidoroun.financialportfolio.security.user.User;
import com.elpidoroun.financialportfolio.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request){
        var user = User.builder()
                .withEmail(request.getEmail())
                .withFullName(request.getFullName())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRole(request.getRole())
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(BAD_REQUEST)
                    .body(AuthenticationResponse.builder()
                            .error("Exception occured while registering a new user. Please see logs for more information.")
                            .build());
        }


        return ResponseEntity.ok(AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build());
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        return userRepository.findByEmail(request.getEmail())
                .map(value -> ResponseEntity.ok(AuthenticationResponse
                    .builder()
                    .token(jwtService.generateToken(value))
                    .build())).orElseGet(() -> ResponseEntity.status(BAD_REQUEST)
                    .body(AuthenticationResponse
                            .builder()
                            .error("Error occured while fetching user from database with email: " + request.getEmail() + " please see logs for more information")
                            .build()));
    }
}