package com.cargopro.loadbooking.dto;

import com.cargopro.loadbooking.entity.BookingStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class BookingResponse {
    private UUID id;
    private UUID loadId;
    private String transporterId;
    private double proposedRate;
    private String comment;
    private BookingStatus status;
    private Timestamp requestedAt;
}
