package com.example.JWTAuthetication.BookingService.controller;

import com.example.JWTAuthetication.BookingService.model.BookingRequest;
import com.example.JWTAuthetication.BookingService.model.BookingResponse;
import com.example.JWTAuthetication.BookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService service;

    @PostMapping("/assign")
    public BookingResponse assignUnits(@RequestBody BookingRequest request) {
        return service.assignUnits(request);
    }
}

