package com.example.challenge_mottu_java.model;

import com.example.challenge_mottu_java.dto.CourtDTO;
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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de pátio")
public class Court {

    @Id
    @Column(name = "acess_code")
    @NotBlank(message = "O código de acesso do pátio é obrigatório")
    @Size(min = 3, max = 150, message = "O código de acesso deve ter entre 3 e 150 caracteres")
    @Schema(description = "Código de acesso do pátio", example = "COURT_MORUMBI")
    private String acessCode;

    @NotBlank(message = "O nome do pátio é obrigatório")
    @Size(min = 1, max = 100, message = "O nome do pátio deve ter entre 1 e 100 caracteres")
    @Column(unique = true)
    @Schema(description = "Nome do pátio", example = "Pátio Morumbi Shopping")
    private String name;

    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    @Schema(description = "Endereço do pátio", example = "Avenida Roque Petroni Júnior, 1089 - Morumbi, São Paulo - SP, 04707-900")
    private String address;

    @NotNull(message = "A capacidade máxima é obrigatória")
    @Min(value = 1, message = "A capacidade máxima deve ser no mínimo 1")
    @Column(name = "max_capacity")
    @Schema(description = "Capacidade máxima do pátio", example = "80")
    private Integer maxCapacity;

    @Builder.Default
    @Column(name = "current_bikes")
    @Schema(description = "Número atual de motos no pátio", example = "3")
    private Integer currentBikes = 0;

    public CourtDTO courtToDTO() {
        return new CourtDTO(
                this.acessCode,
                this.name,
                this.address,
                this.maxCapacity,
                this.currentBikes
        );
    }
}
