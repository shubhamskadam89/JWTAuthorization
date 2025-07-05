package com.example.JWTAuthetication.BookingService.repository;

import com.example.JWTAuthetication.BookingService.model.BookingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingLogRepository extends JpaRepository<BookingLog, Long> {
}
