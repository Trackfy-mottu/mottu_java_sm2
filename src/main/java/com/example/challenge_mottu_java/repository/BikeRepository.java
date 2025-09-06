package com.example.challenge_mottu_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge_mottu_java.model.Bike;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, String> {
    List<Bike> findByCourtAcessCode(String AcessCode);
}
