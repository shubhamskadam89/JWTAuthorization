package com.example.JWTAuthetication.AmbulanceService.model;

import lombok.*;

@Getter

@ToString
public enum AmbulanceStatus {
    AVAILABLE,
    EN_ROUTE,
    BUSY
}
