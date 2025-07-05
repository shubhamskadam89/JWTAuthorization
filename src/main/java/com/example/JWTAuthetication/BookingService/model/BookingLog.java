package com.example.JWTAuthetication.BookingService.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    private double latitude;
    private double longitude;

    private Integer requestedAmbulances;
    private Integer requestedPoliceUnits;
    private Integer requestedFireUnits;

    // Simple CSV storage (optional now if you're using Map)
    @Column(columnDefinition = "TEXT")
    private String assignedAmbulanceIds;

    @Column(columnDefinition = "TEXT")
    private String assignedFireUnitIds;

    // 🔥 JSON string storing {stationId: officerCount}
    @Column(columnDefinition = "TEXT")
    private String assignedPoliceMap;

    private boolean completed;

    private Instant createdAt;

    private Instant completedAt;

    // 🔧 Optional: helper to write map into string
    public void setAssignedPoliceMap(Map<Long, Integer> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.assignedPoliceMap = mapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize assignedPoliceMap", e);
        }
    }

    // 🔧 Optional: helper to parse JSON back into a usable Map
    public Map<Long, Integer> getAssignedPoliceMapAsMap() {
        try {
            if (this.assignedPoliceMap == null || this.assignedPoliceMap.isEmpty()) {
                return Collections.emptyMap();
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(this.assignedPoliceMap, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize assignedPoliceMap", e);
        }
    }
    public void setAssignedPoliceMapJson(String json) {
        this.assignedPoliceMap = json;
    }
}
