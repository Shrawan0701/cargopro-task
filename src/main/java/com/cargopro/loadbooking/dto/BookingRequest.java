package com.cargopro.loadbooking.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookingRequest {
    private UUID loadId;
    private String transporterId;
    private double proposedRate;
    private String comment;
}
