package com.example.challenge_mottu_java.model;

import java.util.List;

import com.example.challenge_mottu_java.Enums.LocationBike;
import com.example.challenge_mottu_java.Enums.ModelsBike;
import com.example.challenge_mottu_java.Enums.StatusBike;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bike {
    @Id
    @NotNull(message = "O número da placa da moto não pode ser nulo")
    private String placa;

    @NotNull(message = "O número do chassi da moto não pode ser nulo")
    private Long idChassi;

    @NotNull(message = "A localizacao da moto é obrigatória")
    private LocationBike localizacao;

    @NotNull(message = "O status da moto é obrigatório")
    private StatusBike status;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pending> pendencias;

    @NotNull(message = "O modelo da moto é obrigatório")
    private ModelsBike modelo;

    @Size(max = 100, message = "O nome do pátio deve ter no máximo 100 caracteres")
    private String patio;

}
