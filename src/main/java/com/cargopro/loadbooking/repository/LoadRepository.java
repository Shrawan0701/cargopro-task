package com.cargopro.loadbooking.repository;

import com.cargopro.loadbooking.entity.Load;
import com.cargopro.loadbooking.entity.LoadStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {
    List<Load> findByShipperIdContainingAndTruckTypeContainingAndStatus(
            String shipperId,
            String truckType,
            LoadStatus status,
            Pageable pageable
    );
}
