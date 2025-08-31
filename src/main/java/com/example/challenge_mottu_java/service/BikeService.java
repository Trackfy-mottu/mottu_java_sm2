package com.example.challenge_mottu_java.service;

import com.example.challenge_mottu_java.dto.BikeDTO;
import com.example.challenge_mottu_java.dto.PendingDTO;
import com.example.challenge_mottu_java.model.Bike;
import com.example.challenge_mottu_java.repository.BikeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeService {

    private final BikeRepository bikeRepository;
    private final CourtService courtService;

    public BikeService(BikeRepository bikeRepository, CourtService courtService) {
        this.bikeRepository = bikeRepository;
        this.courtService = courtService;
    }

    public BikeDTO getBikeByPlaca(String placa){
        Bike bike = bikeRepository.findById(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
        return toDTO(bike);
    }

    public BikeDTO createBike(Bike bike){
        bikeRepository.save(bike);
        courtService.updateBikeCount(bike.getCourt().getAcessCode(), bike.getCourt().getCurrentBikes() + 1);
        return toDTO(bike);
    }

    public BikeDTO updateBike(String placa, @RequestBody @Valid Bike bike){
        bikeRepository.findById(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
        bike.setPlaca(placa);
        bikeRepository.save(bike);
        return toDTO(bike);

    }

    public void deleteBike(String placa){
        Bike bike = bikeRepository.findById(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
        bikeRepository.delete(bike);
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
