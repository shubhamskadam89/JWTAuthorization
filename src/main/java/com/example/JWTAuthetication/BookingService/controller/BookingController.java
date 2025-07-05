package com.example.JWTAuthetication.BookingService.controller;

import com.example.JWTAuthetication.BookingService.model.BookingRequest;
import com.example.JWTAuthetication.BookingService.model.BookingResponse;
import com.example.JWTAuthetication.BookingService.model.BookingLog;
import com.example.JWTAuthetication.BookingService.repository.BookingLogRepository;
import com.example.JWTAuthetication.BookingService.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking", description = "Operations related to emergency unit bookings and dispatches")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingLogRepository bookingLogRepository;



    // 1. Assign units (Ambulance + Police + Fire)

    @Operation(
            summary = "Assign emergency response units",
            description = "Assigns ambulances, police officers, and fire brigades based on location and emergency type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Units successfully assigned"),
            @ApiResponse(responseCode = "400", description = "Invalid request or insufficient units"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/assign")
    public ResponseEntity<BookingResponse> assignUnits(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.assignUnits(request);
        return ResponseEntity.ok(response);
    }

    // 2. Mark booking as completed

    @Operation(
            summary = "Mark booking as completed",
            description = "Releases all units (ambulance, police, fire) associated with a completed booking"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking marked as completed"),
            @ApiResponse(responseCode = "404", description = "Booking ID not found"),
            @ApiResponse(responseCode = "500", description = "Failed to update booking status")
    })
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
