package com.example.JWTAuthetication.BookingService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private IssueType issueType;
    private double latitude;
    private double longitude;

    private int requestedAmbulances;
    private int requestedPoliceUnits;

    @Column(length = 1000)
    private String assignedAmbulanceIds; // comma-separated IDs

    @Column(length = 1000)
    private String assignedPoliceStationIds; // comma-separated IDs

    private Instant createdAt;
}

