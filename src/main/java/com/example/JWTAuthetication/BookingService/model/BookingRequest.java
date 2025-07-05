package com.example.JWTAuthetication.BookingService.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingRequest {
    private double latitude;
    private double longitude;
    private IssueType issueType;
    private int numAmbulances;
    private int numPoliceUnits;
    private int numFireUnits;
}

