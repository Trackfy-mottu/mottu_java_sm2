package com.example.challenge_mottu_java.dto;

import com.example.challenge_mottu_java.Enums.StatusPending;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações de uma pendência da moto")
public record PendingDTO(
        @Schema(description = "ID da pendência", example = "1")
        Long id,

        @Schema(description = "Número de identificação da pendência", example = "101")
        Long number,

        @Schema(description = "Descrição da pendência", example = "Troca de pneu")
        String description,

        @Schema(description = "Status da pendência", example = "PENDENTE")
        StatusPending status,

        @Schema(description = "Placa da moto associada", example = "ABC-1234")
        String placaMoto
) {}
