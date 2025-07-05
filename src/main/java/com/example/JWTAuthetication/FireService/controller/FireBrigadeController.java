package com.example.JWTAuthetication.FireService.controller;

import com.example.JWTAuthetication.FireService.model.FireBrigadeUnit;
import com.example.JWTAuthetication.FireService.model.FireStatus;
import com.example.JWTAuthetication.FireService.service.FireBrigadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fire")
@RequiredArgsConstructor
public class FireBrigadeController {

    private final FireBrigadeService service;

    @PostMapping("/add")
    public FireBrigadeUnit addUnit(@RequestBody FireBrigadeUnit unit) {
        return service.addUnit(unit);
    }

    @GetMapping("/all")
    public List<FireBrigadeUnit> getAll() {
        return service.getAllUnits();
    }

    @PostMapping("/register")
    public FireBrigadeUnit register(@RequestBody FireBrigadeUnit unit) {
        return service.save(unit);
    }

    @GetMapping("/available")
    public List<FireBrigadeUnit> getAvailable() {
        return service.getAvailable();
    }

    @GetMapping("/nearest")
    public List<FireBrigadeUnit> getNearest(@RequestParam double lat,
                                            @RequestParam double lon,
                                            @RequestParam(defaultValue = "1") int count) {
        return service.getNearestAvailable(lat, lon, count);
    }

    @PatchMapping("/status/{id}")
    public void updateStatus(@PathVariable Long id, @RequestParam FireStatus status) {
        service.updateStatus(id, status);
    }

}