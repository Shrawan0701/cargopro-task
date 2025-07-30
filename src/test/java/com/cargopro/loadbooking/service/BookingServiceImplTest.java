package com.cargopro.loadbooking.service;

import com.cargopro.loadbooking.dto.BookingRequest;
import com.cargopro.loadbooking.dto.BookingResponse;
import com.cargopro.loadbooking.entity.*;
import com.cargopro.loadbooking.repository.BookingRepository;
import com.cargopro.loadbooking.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private LoadRepository loadRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBookingSuccess() {
        UUID loadId = UUID.randomUUID();
        BookingRequest request = new BookingRequest();
        request.setLoadId(loadId);
        request.setTransporterId("T1");
        request.setProposedRate(5000);
        request.setComment("test");

        Load load = new Load();
        load.setId(loadId);
        load.setStatus(LoadStatus.POSTED);

        when(loadRepository.findById(loadId)).thenReturn(Optional.of(load));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

        BookingResponse response = bookingService.createBooking(request);

        assertNotNull(response);
        assertEquals(BookingStatus.PENDING, response.getStatus());
    }

    @Test
    void testCreateBookingFailsForCancelledLoad() {
        UUID loadId = UUID.randomUUID();
        BookingRequest request = new BookingRequest();
        request.setLoadId(loadId);

        Load load = new Load();
        load.setStatus(LoadStatus.CANCELLED);

        when(loadRepository.findById(loadId)).thenReturn(Optional.of(load));

        assertThrows(IllegalStateException.class, () -> bookingService.createBooking(request));
    }

    @Test
    void testDeleteBookingWithNoRemaining() {
        UUID bookingId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();

        Load load = new Load();
        load.setId(loadId);

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setLoad(load);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.findByLoadId(loadId)).thenReturn(Collections.emptyList());
        when(loadRepository.findById(loadId)).thenReturn(Optional.of(load));

        bookingService.deleteBooking(bookingId);
        verify(loadRepository).save(any(Load.class));
    }

    @Test
    void testGetBookingByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(bookingRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingService.getBookingById(id));
    }
}