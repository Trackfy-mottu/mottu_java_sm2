package com.example.challenge_mottu_java.model;

import com.example.challenge_mottu_java.Enums.RolesUser;
import com.example.challenge_mottu_java.dto.CourtDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token de autenticação retornado após login")
public record Token(
        @Schema(description = "JWT gerado para autenticação")
        String token,

        @Schema(description = "Email do usuário autenticado", example = "alves@gmail.com")
        String username,

        @Schema(description = "Função do usuário", example = "ADMIN")
        RolesUser role,

        @Schema(description = "Informações do pátio associado ao usuário")
        CourtDTO court
) {}
