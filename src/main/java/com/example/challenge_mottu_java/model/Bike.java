package com.example.challenge_mottu_java.model;

import com.example.challenge_mottu_java.Enums.LocationBike;
import com.example.challenge_mottu_java.Enums.ModelsBike;
import com.example.challenge_mottu_java.Enums.StatusBike;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de moto")
public class Bike {

    @Id
    @NotBlank(message = "O número da placa da moto não pode ser nulo")
    @Schema(description = "Placa da moto", example = "ABC-1234")
    private String placa;

    @NotNull(message = "O número do chassi da moto não pode ser nulo")
    @Schema(description = "ID do chassi da moto", example = "1234567890")
    private Long idChassi;

    @NotNull(message = "A localizacao da moto é obrigatória")
    @Schema(description = "Localização da moto", example = "ESTACIONADA")
    private LocationBike localizacao;

    @NotNull(message = "O status da moto é obrigatório")
    @Schema(description = "Status da moto", example = "ATIVA")
    private StatusBike status;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Schema(description = "Pendências da moto")
    private List<Pending> pendencias = new ArrayList<>();

    @NotNull(message = "O modelo da moto é obrigatório")
    @Schema(description = "Modelo da moto", example = "HONDA_CB500")
    private ModelsBike modelo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "court_acess_code", referencedColumnName = "acess_code")
    @Schema(description = "Pátio da moto")
    private Court court;
}
