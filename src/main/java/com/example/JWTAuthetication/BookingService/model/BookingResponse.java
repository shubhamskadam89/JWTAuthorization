package com.example.JWTAuthetication.BookingService.model;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.FireService.model.FireBrigadeUnit;
import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@ToString
public class BookingResponse {
    private List<Ambulance> assignedAmbulances;
    private List<PoliceStation> assignedPoliceStations;
    private List<FireBrigadeUnit> assignedFireUnits;
    private String statusMessage;

    public BookingResponse(List<Ambulance> ambulances, List<PoliceStation> stations, List<FireBrigadeUnit> fireUnits, String message) {
        this.assignedAmbulances = ambulances;
        this.assignedPoliceStations = stations;
        this.assignedFireUnits = fireUnits;
        this.statusMessage = message;
    }



}

