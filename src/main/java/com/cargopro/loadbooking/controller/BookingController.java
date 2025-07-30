package com.cargopro.loadbooking.controller;

import com.cargopro.loadbooking.dto.BookingRequest;
import com.cargopro.loadbooking.dto.BookingResponse;
import com.cargopro.loadbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Tag(name = "Booking Management", description = "Endpoints for booking operations")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or Load is CANCELLED")
    })
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @Operation(summary = "Get booking by ID")
    @ApiResponse(responseCode = "200", description = "Booking found")
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @Operation(summary = "Get bookings with optional filters")
    @ApiResponse(responseCode = "200", description = "List of bookings")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings(
            @RequestParam(required = false) UUID loadId,
            @RequestParam(required = false) String transporterId,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(bookingService.getBookings(loadId, transporterId, status));
    }

    @Operation(summary = "Update booking by ID")
    @ApiResponse(responseCode = "200", description = "Booking updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable UUID id, @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

    @Operation(summary = "Delete a booking by ID")
    @ApiResponse(responseCode = "200", description = "Booking deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
}
