package com.example.JWTAuthetication.FireService.repository;
import com.example.JWTAuthetication.FireService.model.FireBrigadeUnit;
import com.example.JWTAuthetication.FireService.model.FireStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireBrigadeRepository extends JpaRepository<FireBrigadeUnit, Long> {
    List<FireBrigadeUnit> findByStatus(FireStatus status);
}
