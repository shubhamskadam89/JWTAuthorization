package com.example.JWTAuthetication.BookingService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to assign emergency units")
public class BookingRequest {

    @Schema(description = "Latitude of the incident location", example = "18.5204", required = true)
    private double latitude;

    @Schema(description = "Longitude of the incident location", example = "73.8567", required = true)
    private double longitude;

    @Schema(description = "Type of emergency", example = "FIRE", required = true)
    private IssueType issueType;

    @Schema(description = "Number of ambulances needed", example = "2")
    private int numAmbulances;

    @Schema(description = "Number of police units (officers) needed", example = "3")
    private int numPoliceUnits;

    @Schema(description = "Number of fire brigade units needed", example = "1")
    private int numFireUnits;
}
