package com.example.JWTAuthetication.AmbulanceService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String regNumber;
    private String driverName;

    @Enumerated(EnumType.STRING)
    private AmbulanceStatus status;

    private double latitude;
    private double longitude;

    private Instant lastUpdated;

}
