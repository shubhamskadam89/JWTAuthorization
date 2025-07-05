package com.example.JWTAuthetication.AmbulanceService.controller;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.AmbulanceService.model.AmbulanceStatus;
import com.example.JWTAuthetication.AmbulanceService.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulances")
@CrossOrigin
public class AmbulanceController {

    @Autowired
    private AmbulanceService service;

    @PostMapping
    public Ambulance createAmbulance(@RequestBody Ambulance ambulance) {
        return service.createAmbulance(ambulance);
    }

    @GetMapping
    public List<Ambulance> getAllAmbulances() {
        return service.getAllAmbulances();
    }

    @GetMapping("/{id}")
    public Ambulance getAmbulanceById(@PathVariable Long id) {
        return service.getAmbulanceById(id)
                .orElseThrow(() -> new RuntimeException("Ambulance not found"));
    }

    @GetMapping("/available")
    public List<Ambulance> getAvailableAmbulances() {
        return service.getAvailableAmbulances();
    }

    @PutMapping("/{id}/location")
    public Ambulance updateAmbulanceLocation(
            @PathVariable Long id,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam AmbulanceStatus status
    ) {
        return service.updateLocationAndStatus(id, lat, lng, status);
    }

    @DeleteMapping("/{id}")
    public void deleteAmbulance(@PathVariable Long id) {
        service.deleteAmbulance(id);
    }
}
