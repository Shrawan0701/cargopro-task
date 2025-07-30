package com.cargopro.loadbooking.service;

import com.cargopro.loadbooking.dto.BookingRequest;
import com.cargopro.loadbooking.dto.BookingResponse;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBookingById(UUID id);
    List<BookingResponse> getBookings(UUID loadId, String transporterId, String status);
    BookingResponse updateBooking(UUID id, BookingRequest request);
    void deleteBooking(UUID id);
}
