package com.example.challenge_mottu_java.controller.api;

import com.example.challenge_mottu_java.dto.UserDTO;
import com.example.challenge_mottu_java.model.Token;
import com.example.challenge_mottu_java.model.User;
import com.example.challenge_mottu_java.service.TokenService;
import com.example.challenge_mottu_java.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{username}")
    @Operation(summary = "Buscar usuário", description = "Retorna um usuário pelo username", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UserDTO> getUser(@Parameter(description = "Email do usuário") @PathVariable String username) {
        try {
            UserDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário e retorna token JWT", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<Token> create(@RequestBody @Valid User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.createToken(savedUser));
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{username}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserDTO> updateUser(@Parameter(description = "Email do usuário") @PathVariable String username,
                                              @RequestBody @Valid User newUser) {
        try {
            UserDTO user =  userService.updateUser(username, newUser);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário", description = "Remove um usuário existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID do usuário") @PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
