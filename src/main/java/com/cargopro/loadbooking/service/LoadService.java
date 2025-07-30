package com.cargopro.loadbooking.service;

import com.cargopro.loadbooking.dto.LoadRequest;
import com.cargopro.loadbooking.dto.LoadResponse;

import java.util.List;
import java.util.UUID;

public interface LoadService {
    LoadResponse createLoad(LoadRequest request);
    LoadResponse getLoadById(UUID id);
    List<LoadResponse> getAllLoads(String shipperId, String truckType, String status, int page, int size);
    LoadResponse updateLoad(UUID id, LoadRequest request);
    void deleteLoad(UUID id);
}
