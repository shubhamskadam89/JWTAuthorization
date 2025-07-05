package com.example.JWTAuthetication.AmbulanceService.repository;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.AmbulanceService.model.AmbulanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmbulanceRepository extends JpaRepository<Ambulance,Long> {

    List<Ambulance> findByStatus(AmbulanceStatus status);
}
