package com.example.JWTAuthetication.PoliceService.controller;



import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import com.example.JWTAuthetication.PoliceService.service.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/police-stations")
@CrossOrigin
public class PoliceController {

    @Autowired
    private PoliceService service;

    @PostMapping
    public PoliceStation create(@RequestBody PoliceStation station) {
        return service.createStation(station);
    }

    @GetMapping
    public List<PoliceStation> getAll() {
        return service.getAllStations();
    }

    @PutMapping("/{id}/availability")
    public PoliceStation updateAvailable(@PathVariable Long id, @RequestParam int available) {
        return service.updateAvailability(id, available);
    }


}

