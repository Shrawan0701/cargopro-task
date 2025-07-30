package com.cargopro.loadbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Load {

    @Id
    @GeneratedValue
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

    @Enumerated(EnumType.STRING)
    private LoadStatus status;
}
