package com.example.JWTAuthetication.PoliceService.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PoliceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;         // e.g., Shivajinagar Police Station
    private String zone;                // e.g., Zone 1

    private int totalOfficers;          // max strength
    private int availableOfficers;      // currently available

    private double latitude;
    private double longitude;

    private Instant lastUpdated;
}