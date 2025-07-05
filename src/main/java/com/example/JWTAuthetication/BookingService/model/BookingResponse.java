package com.example.JWTAuthetication.BookingService.model;

import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;
import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingResponse {
    private List<Ambulance> assignedAmbulances;
    private List<PoliceStation> assignedPoliceStations;
    private String statusMessage;
}

