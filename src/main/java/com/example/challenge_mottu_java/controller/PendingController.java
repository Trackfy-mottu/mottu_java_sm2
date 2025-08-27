package com.example.challenge_mottu_java.controller;

import com.example.challenge_mottu_java.dto.PendingDTO;
import com.example.challenge_mottu_java.model.Pending;
import com.example.challenge_mottu_java.model.PendingFilter;
import com.example.challenge_mottu_java.repository.PendingRepository;
import com.example.challenge_mottu_java.specification.PendingSpecification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("pendings")
public class PendingController {

    Logger log = LoggerFactory.getLogger(PendingController.class);

    @Autowired
    private PendingRepository repository;

    @GetMapping
    @Cacheable("pendings")
    public Page<PendingDTO> index(
            PendingFilter filter,
            @PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
        var specification = PendingSpecification.withFilters(filter);
        return repository.findAll(specification, pageable).map(this::toDTO);
    }

    @PostMapping
    @CacheEvict(value = "pendings", allEntries = true)
    @Operation(summary = "Cadastrar pendência", description = "Cria uma nova pendência", responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400"),
    })
    public ResponseEntity<PendingDTO> create(@RequestBody Pending pending) {
        log.info("Criando nova pendência...");
        repository.save(pending);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(pending));
    }

    @GetMapping("{id}")
    public PendingDTO show(@PathVariable Long id) {
        return toDTO(getPending(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "pendings", allEntries = true)
    public void destroy(@PathVariable Long id) {
        log.info("Deletando pendência " + id);
        repository.delete(getPending(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "pendings", allEntries = true)
    public PendingDTO update(@PathVariable Long id, @RequestBody Pending pending) {
        log.info("Atualizando pendência " + id);
        getPending(id); // Verifica se existe
        pending.setId(id);
        return toDTO(repository.save(pending));
    }

    private Pending getPending(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pendência não encontrada"));
    }

    private PendingDTO toDTO(Pending pending) {
        return new PendingDTO(
                pending.getId(),
                pending.getNumber(),
                pending.getDescription(),
                pending.getStatus(),
                pending.getBike() != null ? pending.getBike().getPlaca() : null);
    }
}
