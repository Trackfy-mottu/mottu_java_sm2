package com.example.challenge_mottu_java.controller.api;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenge_mottu_java.model.Credentials;
import com.example.challenge_mottu_java.model.Token;
import com.example.challenge_mottu_java.model.User;
import com.example.challenge_mottu_java.service.AuthService;
import com.example.challenge_mottu_java.service.TokenService;

@RestController
public class AuthController {


    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("api/login")
    public Token login(@RequestBody Credentials credentials){
        User user = (User) authService.loadUserByUsername(credentials.username());
        if (!passwordEncoder.matches(credentials.password(), user.getPassword())){
            throw new BadCredentialsException("Senha incorreta");
        }
        return tokenService.createToken(user);
    }

}
