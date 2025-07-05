package com.example.JWTAuthetication.BookingService.controller;

import com.example.JWTAuthetication.BookingService.model.BookingRequest;
import com.example.JWTAuthetication.BookingService.model.BookingResponse;
import com.example.JWTAuthetication.BookingService.model.BookingLog;
import com.example.JWTAuthetication.BookingService.repository.BookingLogRepository;
import com.example.JWTAuthetication.BookingService.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingLogRepository bookingLogRepository;

    // 1. Assign units (Ambulance + Police + Fire)
    @PostMapping("/assign")
    public ResponseEntity<BookingResponse> assignUnits(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.assignUnits(request);
        return ResponseEntity.ok(response);
    }

    // 2. Mark booking as completed
    @PostMapping("/{id}/complete")
    public ResponseEntity<String> completeBooking(@PathVariable Long id) {
        boolean success = bookingService.markBookingAsCompleted(id);
        if (success) {
            return ResponseEntity.ok("Booking marked as completed and resources released.");
        } else {
            return ResponseEntity.badRequest().body("Booking not found or already completed.");
        }
    }

    // 3. Get all bookings
    @GetMapping
    public ResponseEntity<List<BookingLog>> getAllBookings() {
        List<BookingLog> bookings = bookingLogRepository.findAll();
        return ResponseEntity.ok(bookings);
    }
}
