package com.cargopro.loadbooking.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LoadRequest {
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
}
