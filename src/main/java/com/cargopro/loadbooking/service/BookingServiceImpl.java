package com.cargopro.loadbooking.service;

import com.cargopro.loadbooking.dto.BookingRequest;
import com.cargopro.loadbooking.dto.BookingResponse;
import com.cargopro.loadbooking.entity.*;
import com.cargopro.loadbooking.repository.BookingRepository;
import com.cargopro.loadbooking.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final LoadRepository loadRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Load load = loadRepository.findById(request.getLoadId())
                .orElseThrow(() -> new EntityNotFoundException("Load not found"));

        if (load.getStatus() == LoadStatus.CANCELLED) {
            throw new IllegalStateException("Cannot create booking for a cancelled load");
        }

        Booking booking = new Booking();
        booking.setLoad(load);
        booking.setTransporterId(request.getTransporterId());
        booking.setProposedRate(request.getProposedRate());
        booking.setComment(request.getComment());
        booking.setStatus(BookingStatus.PENDING);
        booking.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        load.setStatus(LoadStatus.BOOKED); // update load status
        loadRepository.save(load);

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse getBookingById(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return mapToResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookings(UUID loadId, String transporterId, String status) {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .filter(b -> (loadId == null || b.getLoad().getId().equals(loadId)) &&
                        (transporterId == null || b.getTransporterId().equalsIgnoreCase(transporterId)) &&
                        (status == null || b.getStatus().name().equalsIgnoreCase(status)))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse updateBooking(UUID id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        booking.setProposedRate(request.getProposedRate());
        booking.setComment(request.getComment());

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        UUID loadId = booking.getLoad().getId();
        bookingRepository.deleteById(id);

        List<Booking> remaining = bookingRepository.findByLoadId(loadId);

        // If no bookings left OR all are ACCEPTED/REJECTED → revert Load status to POSTED
        boolean allAcceptedOrRejected = remaining.stream().allMatch(
                b -> b.getStatus() == BookingStatus.ACCEPTED || b.getStatus() == BookingStatus.REJECTED
        );

        if (remaining.isEmpty() || allAcceptedOrRejected) {
            Load load = loadRepository.findById(loadId).orElseThrow();
            load.setStatus(LoadStatus.POSTED);
            loadRepository.save(load);
        }
    }

    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setLoadId(booking.getLoad().getId());
        response.setTransporterId(booking.getTransporterId());
        response.setProposedRate(booking.getProposedRate());
        response.setComment(booking.getComment());
        response.setStatus(booking.getStatus());
        response.setRequestedAt(booking.getRequestedAt());
        return response;
    }
}
