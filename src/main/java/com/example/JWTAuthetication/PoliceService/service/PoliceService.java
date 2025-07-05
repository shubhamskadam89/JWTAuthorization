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

}
