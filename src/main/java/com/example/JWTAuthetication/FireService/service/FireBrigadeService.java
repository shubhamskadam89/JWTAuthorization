package com.example.JWTAuthetication.FireService.service;
import com.example.JWTAuthetication.FireService.model.FireBrigadeUnit;
import com.example.JWTAuthetication.FireService.model.FireStatus;
import com.example.JWTAuthetication.FireService.repository.FireBrigadeRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor @Getter @Setter @ToString
public class FireBrigadeService {

    private final FireBrigadeRepository fireRepo;

    public FireBrigadeUnit save(FireBrigadeUnit unit) {
        unit.setLastUpdated(Instant.now());
        return fireRepo.save(unit);
    }

    public List<FireBrigadeUnit> getAll() {
        return fireRepo.findAll();
    }

    public Optional<FireBrigadeUnit> getById(Long id) {
        return fireRepo.findById(id);
    }

    public List<FireBrigadeUnit> getAvailable() {
        return fireRepo.findByStatus(FireStatus.AVAILABLE);
    }

    public List<FireBrigadeUnit> getNearestAvailable(double lat, double lon, int count) {
        return fireRepo.findByStatus(FireStatus.AVAILABLE).stream()
                .sorted(Comparator.comparingDouble(u ->
                        Math.sqrt(Math.pow(u.getLatitude() - lat, 2) + Math.pow(u.getLongitude() - lon, 2))))
                .limit(count)
                .toList();
    }

    public void updateStatus(Long id, FireStatus status) {
        fireRepo.findById(id).ifPresent(unit -> {
            unit.setStatus(status);
            unit.setLastUpdated(Instant.now());
            fireRepo.save(unit);
        });
    }
    public FireBrigadeUnit addUnit(FireBrigadeUnit unit) {
        unit.setLastUpdated(Instant.now());
        return fireRepo.save(unit);
    }

    public List<FireBrigadeUnit> getAllUnits() {
        return fireRepo.findAll();
    }
}
