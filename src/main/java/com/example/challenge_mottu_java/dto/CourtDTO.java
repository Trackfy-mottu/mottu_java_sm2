package com.example.challenge_mottu_java.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações de um pátio")
public record CourtDTO (
        @Schema(description = "Código de acesso do pátio", example = "COURT_MORUMBI")
        String acessCode,

        @Schema(description = "Nome do pátio", example = "Pátio Morumbi Shopping")
        String name,

        @Schema(description = "Endereço do pátio", example = "Avenida Roque Petroni Júnior, 1089 - Morumbi, São Paulo - SP, 04707-900")
        String address,

        @Schema(description = "Capacidade máxima de motos no pátio", example = "80")
        Integer maxCapacity,

        @Schema(description = "Número atual de motos no pátio", example = "3")
        Integer currentBikes
) {}
