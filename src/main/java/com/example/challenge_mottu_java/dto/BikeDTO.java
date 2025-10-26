package com.example.challenge_mottu_java.dto;

import com.example.challenge_mottu_java.Enums.LocationBike;
import com.example.challenge_mottu_java.Enums.ModelsBike;
import com.example.challenge_mottu_java.Enums.StatusBike;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Informações de uma moto")
public record BikeDTO(
        @Schema(description = "Placa da moto", example = "ABC-1234")
        String placa,

        @Schema(description = "Status da moto", example = "ATIVA")
        StatusBike status,

        @Schema(description = "Lista de pendências da moto")
        List<PendingDTO> pendencias,

        @Schema(description = "Modelo da moto", example = "HONDA_CB500")
        ModelsBike modelo,

        @Schema(description = "Informações do pátio onde a moto está")
        CourtDTO court,

        @Schema(description = "Localização atual da moto", example = "ESTACIONADA")
        LocationBike localizacao,

        @Schema(description = "ID do chassi da moto", example = "1234567890")
        Long idChassi
) {}
