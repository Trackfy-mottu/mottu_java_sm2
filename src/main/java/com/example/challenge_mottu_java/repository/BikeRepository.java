package com.example.challenge_mottu_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge_mottu_java.model.Bike;

public interface BikeRepository extends JpaRepository<Bike, String> {

}
