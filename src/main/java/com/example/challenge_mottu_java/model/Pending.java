package com.example.challenge_mottu_java.model;

import com.example.challenge_mottu_java.Enums.StatusPending;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pendência de uma moto")
public class Pending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da pendência", example = "1")
    private Long id;

    @NotNull(message = "O número da pendência é obrigatório")
    @Min(value = 1, message = "O número da pendência deve ser no mínimo 1")
    @Max(value = 3, message = "O número da pendência deve ser no máximo 3")
    @Schema(description = "Número da pendência", example = "1")
    private Long number;

    @Size(max = 400, message = "A descrição deve ter no máximo 400 caracteres")
    @Schema(description = "Descrição da pendência", example = "Troca de pneu")
    private String description;

    @NotNull(message = "O status da pendência é obrigatório")
    @Schema(description = "Status da pendência", example = "PENDENTE")
    private StatusPending status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bike_id", referencedColumnName = "placa")
    @Schema(description = "Moto associada à pendência")
    private Bike bike;
}
