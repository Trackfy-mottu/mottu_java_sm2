package com.example.challenge_mottu_java.controller;

import com.example.challenge_mottu_java.dto.BikeDTO;
import com.example.challenge_mottu_java.dto.PendingDTO;
import com.example.challenge_mottu_java.model.Bike;
import com.example.challenge_mottu_java.repository.BikeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("bike")
public class BikeController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BikeRepository bikeRepository;

    @GetMapping("{placa}")
    public ResponseEntity<BikeDTO> getBikeByPlaca(@PathVariable String placa) {
        log.info("Buscando moto com placa: {}", placa);

        Bike bike = bikeRepository.findById(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));

        BikeDTO dto = toDTO(bike);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @CacheEvict(value = "bike", allEntries = true)
    @Operation(summary = "Cadastrar moto", description = "Insere uma moto", responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400"),
    })
    public ResponseEntity<Bike> create(@RequestBody @Valid Bike bike) {
        log.info("Cadastrando moto: ", bike.getPlaca());
        bikeRepository.save(bike);
        return ResponseEntity.status(HttpStatus.CREATED).body(bike);
    }

    @DeleteMapping("{placa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable String placa) {
        log.info("Apagando moto {}", placa);
        Bike bike = bikeRepository.findById(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
        bikeRepository.delete(bike);
    }

    @PutMapping("{placa}")
    public ResponseEntity<Bike> update(@PathVariable String placa, @RequestBody @Valid Bike bike) {
        log.info("Atualizando moto: {}", placa);

        bikeRepository.findById(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));

        bike.setPlaca(placa);
        bikeRepository.save(bike);

        return ResponseEntity.ok(bike);
    }

    private BikeDTO toDTO(Bike bike) {
        List<PendingDTO> pendencias = bike.getPendencias().stream()
                .map(p -> new PendingDTO(
                        p.getId(),
                        p.getNumber(),
                        p.getDescription(),
                        p.getStatus(),
                        p.getBike() != null ? p.getBike().getPlaca() : null))
                .collect(Collectors.toList());

        return new BikeDTO(
                bike.getPlaca(),
                bike.getStatus(),
                pendencias,
                bike.getModelo(),
                bike.getLocalizacao());
    }
}
