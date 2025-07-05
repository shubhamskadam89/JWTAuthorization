package com.example.JWTAuthetication.BookingService.service;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.AmbulanceService.model.AmbulanceStatus;
import com.example.JWTAuthetication.AmbulanceService.service.AmbulanceService;
import com.example.JWTAuthetication.BookingService.model.BookingLog;
import com.example.JWTAuthetication.BookingService.model.BookingRequest;
import com.example.JWTAuthetication.BookingService.model.BookingResponse;
import com.example.JWTAuthetication.BookingService.repository.BookingLogRepository;
import com.example.JWTAuthetication.FireService.model.FireBrigadeUnit;
import com.example.JWTAuthetication.FireService.model.FireStatus;
import com.example.JWTAuthetication.FireService.service.FireBrigadeService;
import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import com.example.JWTAuthetication.PoliceService.service.PoliceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingLogRepository bookingLogRepository;

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private PoliceService policeService;

    @Autowired
    private FireBrigadeService fireBrigadeService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public BookingResponse assignUnits(BookingRequest request) {
        double lat = request.getLatitude();
        double lng = request.getLongitude();

        int neededAmb = request.getNumAmbulances();
        int neededPolice = request.getNumPoliceUnits();
        int neededFire = request.getNumFireUnits();

        List<Ambulance> ambulances = new ArrayList<>();
        List<PoliceStation> assignedStations = new ArrayList<>();
        List<FireBrigadeUnit> fireUnits = new ArrayList<>();
        Map<Long, Integer> assignedPoliceMap = new HashMap<>();

        // 🚑 Ambulance assignment
        List<Ambulance> availableAmbulances = ambulanceService.getAvailableAmbulances().stream()
                .sorted(Comparator.comparingDouble(a -> distance(lat, lng, a.getLatitude(), a.getLongitude())))
                .limit(neededAmb)
                .peek(a -> {
                    a.setStatus(AmbulanceStatus.EN_ROUTE);
                    a.setLastUpdated(Instant.now());
                    ambulanceService.createAmbulance(a);
                })
                .toList();
        ambulances.addAll(availableAmbulances);

        // 👮 Progressive Police Station Assignment (nearest first)
        int remainingPolice = neededPolice;
        List<PoliceStation> sortedStations = policeService.getAllStations().stream()
                .sorted(Comparator.comparingDouble(s -> distance(lat, lng, s.getLatitude(), s.getLongitude())))
                .toList();

        for (PoliceStation station : sortedStations) {
            if (remainingPolice <= 0) break;
            int available = station.getAvailableOfficers();
            if (available > 0) {
                int assign = Math.min(available, remainingPolice);
                station.setAvailableOfficers(available - assign);
                station.setLastUpdated(Instant.now());
                policeService.updateAvailability(station.getId(), station.getAvailableOfficers());
                assignedStations.add(station);
                assignedPoliceMap.put(station.getId(), assign);
                remainingPolice -= assign;
            }
        }

        // 🔥 Fire Brigade Unit Assignment
        List<FireBrigadeUnit> availableFire = fireBrigadeService.getNearestAvailable(lat, lng, neededFire).stream()
                .peek(f -> fireBrigadeService.updateStatus(f.getId(), FireStatus.ON_DUTY))
                .toList();
        fireUnits.addAll(availableFire);

        // 📝 Log
        BookingLog log = new BookingLog();
        log.setIssueType(request.getIssueType());
        log.setLatitude(lat);
        log.setLongitude(lng);
        log.setRequestedAmbulances(neededAmb);
        log.setRequestedPoliceUnits(neededPolice);
        log.setRequestedFireUnits(neededFire);
        log.setAssignedAmbulanceIds(joinIds(ambulances.stream().map(Ambulance::getId).toList()));
        log.setAssignedFireUnitIds(joinIds(fireUnits.stream().map(FireBrigadeUnit::getId).toList()));
        log.setCompleted(false);
        log.setCreatedAt(Instant.now());

        try {
            log.setAssignedPoliceMap(assignedPoliceMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize police map", e);
        }

        bookingLogRepository.save(log);

        // ✅ Status
        StringBuilder statusMsg = new StringBuilder("🚨 Dispatch Summary: ");
        statusMsg.append(ambulances.size()).append("/").append(neededAmb).append(" Ambulance(s), ");
        statusMsg.append(neededPolice - remainingPolice).append("/").append(neededPolice).append(" Police Officer(s), ");
        statusMsg.append(fireUnits.size()).append("/").append(neededFire).append(" Fire Unit(s)");

        if (ambulances.size() < neededAmb || remainingPolice > 0 || fireUnits.size() < neededFire) {
            statusMsg.append(" ⚠️ Partial Assignment");
        } else {
            statusMsg.append(" ✅ Full Assignment");
        }

        return new BookingResponse(ambulances, assignedStations, fireUnits, statusMsg.toString());
    }

    @Transactional
    public boolean markBookingAsCompleted(Long bookingId) {
        Optional<BookingLog> optionalLog = bookingLogRepository.findById(bookingId);
        if (optionalLog.isEmpty()) return false;

        BookingLog log = optionalLog.get();
        if (log.isCompleted()) return true;

        // 🚑 Release Ambulances
        if (log.getAssignedAmbulanceIds() != null) {
            Arrays.stream(log.getAssignedAmbulanceIds().split(","))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .map(Long::valueOf)
                    .forEach(id -> ambulanceService.getAmbulanceById(id).ifPresent(amb -> {
                        amb.setStatus(AmbulanceStatus.AVAILABLE);
                        amb.setLastUpdated(Instant.now());
                        ambulanceService.createAmbulance(amb);
                    }));
        }

        // 👮 Release Police Officers based on map
        if (log.getAssignedPoliceMap() != null) {
            try {
                Map<Long, Integer> assignedMap = objectMapper.readValue(
                        log.getAssignedPoliceMap(),
                        new TypeReference<>() {}
                );

                for (Map.Entry<Long, Integer> entry : assignedMap.entrySet()) {
                    Long stationId = entry.getKey();
                    Integer officersToRelease = entry.getValue();

                    policeService.getAllStations().stream()
                            .filter(p -> p.getId().equals(stationId))
                            .findFirst()
                            .ifPresent(ps -> {
                                ps.setAvailableOfficers(ps.getAvailableOfficers() + officersToRelease);
                                ps.setLastUpdated(Instant.now());
                                policeService.updateAvailability(ps.getId(), ps.getAvailableOfficers());
                            });
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize assignedPoliceMap", e);
            }
        }

        // 🔥 Release Fire Units
        if (log.getAssignedFireUnitIds() != null) {
            Arrays.stream(log.getAssignedFireUnitIds().split(","))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .map(Long::valueOf)
                    .forEach(id -> fireBrigadeService.updateStatus(id, FireStatus.AVAILABLE));
        }

        log.setCompleted(true);
        bookingLogRepository.save(log);
        return true;
    }

    private String joinIds(List<Long> ids) {
        return ids.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }
}
