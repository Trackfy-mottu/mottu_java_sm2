package com.example.challenge_mottu_java.controller.api;

import com.example.challenge_mottu_java.dto.CourtDTO;
import com.example.challenge_mottu_java.model.Court;
import com.example.challenge_mottu_java.service.CourtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/court")
public class CourtController {

    private final CourtService courtService;
    Logger log = LoggerFactory.getLogger(getClass());

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping("/{acessCode}")
    @Operation(summary = "Buscar pátio por código de acesso", description = "Retorna um pátio específico com base no código de acesso", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Pátio encontrado")
    @ApiResponse(responseCode = "404", description = "Pátio não encontrado")
    public ResponseEntity<CourtDTO> getCourtByAcessCode(@Parameter(description = "Código de acesso do pátio") @PathVariable String acessCode) {
        try {
            return ResponseEntity.ok(courtService.getCourtByAcessCode(acessCode).courtToDTO());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os pátios", description = "Retorna todos os pátios cadastrados")
    public ResponseEntity<List<CourtDTO>> getAllCourts() {
        List<CourtDTO> courts = courtService.getAllCourts();
        return ResponseEntity.ok(courts);
    }

    @PostMapping
    @Operation(summary = "Cadastrar pátio", description = "Cria um novo pátio no sistema", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Pátio criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<CourtDTO> createCourt(@RequestBody @Valid Court court) {
        try {
            CourtDTO newCourt = courtService.createCourt(court);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCourt);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{acessCode}")
    @Operation(summary = "Atualizar pátio", description = "Atualiza os dados de um pátio existente", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CourtDTO> updateCourt(@Parameter(description = "Código de acesso do pátio") @PathVariable String acessCode,
                                                @RequestBody @Valid Court newCourt) {
        try {
            CourtDTO court = courtService.updateCourt(acessCode, newCourt);
            return ResponseEntity.ok(court);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{acessCode}")
    @Operation(summary = "Remover pátio", description = "Remove um pátio existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Pátio removido com sucesso")
    public ResponseEntity<Void> deleteCourt(@Parameter(description = "Código de acesso do pátio") @PathVariable String acessCode) {
        try {
            courtService.deleteCourt(acessCode);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
