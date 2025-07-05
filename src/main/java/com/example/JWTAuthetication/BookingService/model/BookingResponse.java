package com.example.JWTAuthetication.BookingService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import com.example.JWTAuthetication.FireService.model.FireBrigadeUnit;
import com.example.JWTAuthetication.PoliceService.model.PoliceStation;
import com.example.JWTAuthetication.AmbulanceService.model.Ambulance;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response containing the assigned units and status message")
public class BookingResponse {

    @Schema(description = "List of assigned ambulances")
    private List<Ambulance> assignedAmbulances;

    @Schema(description = "List of assigned police stations")
    private List<PoliceStation> assignedPoliceStations;

    @Schema(description = "List of assigned fire brigade units")
    private List<FireBrigadeUnit> assignedFireUnits;

    @Schema(description = "Summary message about the assignment status", example = "✅ All units successfully assigned.")
    private String statusMessage;
}
