package com.example.JWTAuthetication.BookingService;

import java.time.LocalDateTime;

public class SOSRequest {
    private String id;
    private String phoneNumber;
    private String location;
    private String symptom;
    private SeverityLevel severity;
    private BookingStatus status;
    private LocalDateTime timestamp;
}

