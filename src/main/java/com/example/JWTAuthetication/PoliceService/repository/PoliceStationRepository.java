package com.example.JWTAuthetication.PoliceService.repository;

import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoliceStationRepository extends JpaRepository<PoliceStation, Long> {
    List<PoliceStation> findByAvailableOfficersGreaterThan(int threshold);
}
