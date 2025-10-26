package com.example.challenge_mottu_java.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenge_mottu_java.model.Credentials;
import com.example.challenge_mottu_java.model.Token;
import com.example.challenge_mottu_java.model.User;
import com.example.challenge_mottu_java.service.AuthService;
import com.example.challenge_mottu_java.service.TokenService;
import org.springframework.web.server.ResponseStatusException;

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

    @Operation(summary = "Login do usuário", description = "Autentica usuário e retorna token JWT com informações do usuário e do pátio")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais incorretas")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PostMapping("api/login")
    public Token login(@RequestBody Credentials credentials){
        try {
            User user = (User) authService.loadUserByUsername(credentials.username());
            if (!passwordEncoder.matches(credentials.password(), user.getPassword())){
                throw new BadCredentialsException("Senha incorreta");
            }
            return tokenService.createToken(user);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais incorretas");
        }
    }

}
