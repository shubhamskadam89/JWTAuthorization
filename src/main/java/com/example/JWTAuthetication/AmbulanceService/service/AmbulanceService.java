package com.example.JWTAuthetication.AmbulanceService.service;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.AmbulanceService.model.AmbulanceStatus;
import com.example.JWTAuthetication.AmbulanceService.repository.AmbulanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {
    @Autowired
    private AmbulanceRepository repository;

    public Ambulance createAmbulance(Ambulance ambulance) {
        return repository.save(ambulance);
    }

    public List<Ambulance> getAllAmbulances() {
        return repository.findAll();
    }

    public Optional<Ambulance> getAmbulanceById(Long id) {
        return repository.findById(id);
    }

    public List<Ambulance> getAvailableAmbulances() {
        return repository.findByStatus(AmbulanceStatus.AVAILABLE);
    }

    public Ambulance updateLocationAndStatus(Long id, double lat, double lng, AmbulanceStatus status) {
        Ambulance amb = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambulance not found"));

        amb.setLatitude(lat);
        amb.setLongitude(lng);
        amb.setStatus(status);
        amb.setLastUpdated(Instant.now());

        return repository.save(amb);
    }

    public void deleteAmbulance(Long id) {
        repository.deleteById(id);
    }
}
