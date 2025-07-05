package com.example.JWTAuthetication.BookingService.service;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.AmbulanceService.model.AmbulanceStatus;
import com.example.JWTAuthetication.AmbulanceService.repository.AmbulanceRepository;
import com.example.JWTAuthetication.BookingService.model.BookingLog;
import com.example.JWTAuthetication.BookingService.model.BookingRequest;
import com.example.JWTAuthetication.BookingService.model.BookingResponse;
import com.example.JWTAuthetication.BookingService.repository.BookingLogRepository;
import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import com.example.JWTAuthetication.PoliceService.repository.PoliceStationRepository;
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
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private PoliceStationRepository policeStationRepository;

    public BookingResponse assignUnits(BookingRequest request) {
        List<Ambulance> ambulances = getNearestAvailableAmbulances(request.getLatitude(), request.getLongitude(), request.getNumAmbulances());
        List<PoliceStation> stations = getNearestAvailablePoliceStations(request.getLatitude(), request.getLongitude(), request.getNumPoliceUnits());

        // Update ambulance status
        ambulances.forEach(a -> {
            a.setStatus(AmbulanceStatus.EN_ROUTE);
            a.setLastUpdated(Instant.now());
        });
        ambulanceRepository.saveAll(ambulances);

        // Update police station availability
        stations.forEach(s -> {
            s.setAvailableOfficers(s.getAvailableOfficers() - 1);
            s.setLastUpdated(Instant.now());
        });
        policeStationRepository.saveAll(stations);

        return new BookingResponse(ambulances, stations, "Units Assigned Successfully");
    }

    private List<Ambulance> getNearestAvailableAmbulances(double lat, double lng, int count) {
        List<Ambulance> available = ambulanceRepository.findByStatus(AmbulanceStatus.AVAILABLE);
        return available.stream()
                .sorted(Comparator.comparingDouble(a -> distance(lat, lng, a.getLatitude(), a.getLongitude())))
                .limit(count)
                .collect(Collectors.toList());
    }

    private List<PoliceStation> getNearestAvailablePoliceStations(double lat, double lng, int count) {
        List<PoliceStation> available = policeStationRepository.findByAvailableOfficersGreaterThan(0);
        return available.stream()
                .sorted(Comparator.comparingDouble(s -> distance(lat, lng, s.getLatitude(), s.getLongitude())))
                .limit(count)
                .collect(Collectors.toList());
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }

//    BookingLog bookingLog = new BookingLog();
//    bookingLog.setIssueType(request.getIssueType());
//    bookingLog.setLatitude(request.getLatitude());
//    bookingLog.setLongitude(request.getLongitude());
//    bookingLog.setRequestedAmbulances(request.getNumAmbulances());
//    bookingLog.setRequestedPoliceUnits(request.getNumPoliceUnits());
//    bookingLog.setAssignedAmbulanceIds(ambulances.stream()
//            .map(a -> String.valueOf(a.getId()))
//                .collect(Collectors.joining(",")));
//    bookingLog.setAssignedPoliceStationIds(stations.stream()
//            .map(s -> String.valueOf(s.getId()))
//                .collect(Collectors.joining(",")));
//    bookingLog.setCreatedAt(Instant.now());
//
//    bookingLogRepository.save(bookingLog);

}
