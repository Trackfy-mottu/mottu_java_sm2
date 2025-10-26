package com.example.challenge_mottu_java.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciais de login")
public record Credentials(
        @Schema(description = "Email do usuário", example = "alves@gmail.com") String username,
        @Schema(description = "Senha do usuário", example = "12345") String password
) {}
