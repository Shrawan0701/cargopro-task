package com.cargopro.loadbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "load_id")
    private Load load;

    private String transporterId;
    private double proposedRate;
    private String comment;
    private Timestamp requestedAt;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
