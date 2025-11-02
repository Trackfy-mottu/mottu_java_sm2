package com.example.challenge_mottu_java.controller.api;

import com.example.challenge_mottu_java.dto.BikeDTO;
import com.example.challenge_mottu_java.model.Bike;
import com.example.challenge_mottu_java.service.BikeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/bike")
public class BikeController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/{placa}")
    @Operation(summary = "Buscar moto por placa", description = "Retorna uma moto com base na placa fornecida", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Moto encontrada")
    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    public ResponseEntity<BikeDTO> getBikeByPlaca(@Parameter(description = "Placa da moto") @PathVariable String placa) {
        try {
            BikeDTO bikeDTO = bikeService.getBikeByPlaca(placa);
            return ResponseEntity.ok(bikeDTO);
        }catch (ResponseStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/court/{acessCode}")
    @Operation(summary = "Buscar motos por pátio", description = "Retorna todas as motos cadastradas em um pátio específico", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BikeDTO>> getBikeByAcessCode(@Parameter(description = "Código de acesso do pátio") @PathVariable String acessCode) {
        try {
            List<BikeDTO> bikes = bikeService.getBikeByAcessCode(acessCode);
            return ResponseEntity.ok(bikes);
        }catch (ResponseStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @CacheEvict(value = "bike", allEntries = true)
    @Operation(summary = "Cadastrar moto", description = "Insere uma nova moto no sistema", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Moto criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<BikeDTO> create(@RequestBody @Valid Bike bike) {
        try {
            BikeDTO bikeDTO = bikeService.createBike(bike);
            return ResponseEntity.status(HttpStatus.CREATED).body(bikeDTO);
        }catch (ResponseStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{placa}")
    @Operation(summary = "Atualizar moto", description = "Atualiza os dados de uma moto existente", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BikeDTO> update(@Parameter(description = "Placa da moto") @PathVariable String placa, @RequestBody @Valid Bike bike) {
        try {
            BikeDTO bikeDTO = bikeService.updateBike(placa, bike);
            return ResponseEntity.ok(bikeDTO);
        }catch (ResponseStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{placa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover moto", description = "Remove uma moto cadastrada", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Moto removida com sucesso")
    public ResponseEntity<Void> destroy(@Parameter(description = "Placa da moto") @PathVariable String placa) {
        try {
            bikeService.deleteBike(placa);
            return ResponseEntity.noContent().build();
        }catch (ResponseStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
}
