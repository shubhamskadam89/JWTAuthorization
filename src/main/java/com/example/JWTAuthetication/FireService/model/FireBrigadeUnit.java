package com.example.JWTAuthetication.FireService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FireBrigadeUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitNumber;
    private String stationName;

    @Enumerated(EnumType.STRING)
    private FireStatus status;

    private double latitude;
    private double longitude;
    private Instant lastUpdated;
}
