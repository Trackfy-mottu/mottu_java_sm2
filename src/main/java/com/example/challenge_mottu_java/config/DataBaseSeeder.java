package com.example.challenge_mottu_java.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.challenge_mottu_java.Enums.LocationBike;
import com.example.challenge_mottu_java.Enums.ModelsBike;
import com.example.challenge_mottu_java.Enums.StatusBike;
import com.example.challenge_mottu_java.Enums.StatusPending;
import com.example.challenge_mottu_java.model.Bike;
import com.example.challenge_mottu_java.model.Pending;
import com.example.challenge_mottu_java.repository.BikeRepository;
import com.example.challenge_mottu_java.repository.PendingRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class DataBaseSeeder {

        @Autowired
        private BikeRepository motoRepository;

        @Autowired
        private PendingRepository pendenciaRepository;

        @PostConstruct
        public void init() {
                Bike moto1 = Bike.builder()
                                .placa("ABC1234")
                                .idChassi(1001L)
                                .localizacao(LocationBike.DENTRO)
                                .status(StatusBike.EmUso)
                                .modelo(ModelsBike.Sport)
                                .patio("Pátio Central")
                                .idUsuario(1L)
                                .build();

                Bike moto2 = Bike.builder()
                                .placa("XYZ5678")
                                .idChassi(1002L)
                                .localizacao(LocationBike.DENTRO)
                                .status(StatusBike.EmUso)
                                .modelo(ModelsBike.E)
                                .patio("Pátio Leste")
                                .build();

                Bike moto3 = Bike.builder()
                                .placa("GHJ9101")
                                .idChassi(0321L)
                                .localizacao(LocationBike.FORA)
                                .status(StatusBike.EmUso)
                                .modelo(ModelsBike.Pop)
                                .build();
                motoRepository.saveAll(Arrays.asList(moto1, moto2, moto3));

                Pending pend1 = Pending.builder()
                                .number(1L)
                                .description("Falta de documentação")
                                .status(StatusPending.EmAndamento)
                                .bike(moto1)
                                .build();

                Pending pend2 = Pending.builder()
                                .number(2L)
                                .description("Multa em aberto")
                                .status(StatusPending.Pendente)
                                .bike(moto2)
                                .build();

                pendenciaRepository.saveAll(Arrays.asList(pend1, pend2));
        }
}
