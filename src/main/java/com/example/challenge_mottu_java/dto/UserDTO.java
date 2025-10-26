package com.example.challenge_mottu_java.dto;

import com.example.challenge_mottu_java.Enums.RolesUser;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações de um usuário")
public record UserDTO(
        @Schema(description = "ID do usuário", example = "2")
        Long id,

        @Schema(description = "Email do usuário", example = "alves@gmail.com")
        String username,

        @Schema(description = "Nome completo do usuário", example = "Guilherme Alves")
        String name,

        @Schema(description = "Informações do pátio do usuário")
        CourtDTO courtDTO,

        @Schema(description = "Função do usuário", example = "ADMIN")
        RolesUser role
) {}
