package com.example.JWTAuthetication.PoliceService.service;


import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import com.example.JWTAuthetication.PoliceService.repository.PoliceStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class PoliceService {

    @Autowired
    private PoliceStationRepository repository;

    public PoliceStation createStation(PoliceStation station) {
        station.setLastUpdated(Instant.now());
        return repository.save(station);
    }

    public List<PoliceStation> getAllStations() {
        return repository.findAll();
    }

    public PoliceStation updateAvailability(Long id, int availableOfficers) {
        PoliceStation ps = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Police station not found"));

        ps.setAvailableOfficers(availableOfficers);
        ps.setLastUpdated(Instant.now());
        return repository.save(ps);
    }

    public PoliceStation assignUnit(double lat, double lng) {
        int MIN_REQUIRED = 2;
        List<PoliceStation> candidates = repository.findByAvailableOfficersGreaterThan(MIN_REQUIRED);

        return candidates.stream()
                .min(Comparator.comparingDouble(p -> distance(lat, lng, p.getLatitude(), p.getLongitude())))
                .map(station -> {
                    station.setAvailableOfficers(station.getAvailableOfficers() - 1);
                    station.setLastUpdated(Instant.now());
                    return repository.save(station);
                })
                .orElseThrow(() -> new RuntimeException("No available police station with minimum officers"));
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }
}
