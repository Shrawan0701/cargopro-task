package com.cargopro.loadbooking.dto;

import com.cargopro.loadbooking.entity.LoadStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class LoadResponse {
    private UUID id;
    private String shipperId;
    private String loadingPoint;
    private String unloadingPoint;
    private Timestamp loadingDate;
    private Timestamp unloadingDate;
    private String productType;
    private String truckType;
    private int noOfTrucks;
    private double weight;
    private String comment;
    private Timestamp datePosted;
    private LoadStatus status;
}
