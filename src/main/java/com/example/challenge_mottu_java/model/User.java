package com.example.challenge_mottu_java.model;

import com.example.challenge_mottu_java.Enums.RolesUser;
import com.example.challenge_mottu_java.dto.CourtDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String username;

    @NotBlank
    @Size(min = 1, max = 100)
    private String password;

    @Size(min = 1, max = 255)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "court_acess_code", referencedColumnName = "acess_code")
    private Court court;

    @Enumerated(EnumType.STRING)
    private RolesUser role;

    private CourtDTO courtToDTO(Court court) {
        return new CourtDTO(
                court.getAcessCode(),
                court.getName(),
                court.getAddress(),
                court.getMaxCapacity(),
                court.getCurrentBikes()
        );
    }

}
