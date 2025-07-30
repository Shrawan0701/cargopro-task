package com.cargopro.loadbooking.service;

import com.cargopro.loadbooking.dto.LoadRequest;
import com.cargopro.loadbooking.dto.LoadResponse;
import com.cargopro.loadbooking.entity.Load;
import com.cargopro.loadbooking.entity.LoadStatus;
import com.cargopro.loadbooking.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadServiceImpl implements LoadService {

    private final LoadRepository loadRepository;

    @Override
    public LoadResponse createLoad(LoadRequest request) {
        Load load = new Load();
        BeanUtils.copyProperties(request, load);
        load.setStatus(LoadStatus.POSTED);
        load.setDatePosted(new Timestamp(System.currentTimeMillis()));
        return mapToResponse(loadRepository.save(load));
    }

    @Override
    public LoadResponse getLoadById(UUID id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Load not found"));
        return mapToResponse(load);
    }

    @Override
    public List<LoadResponse> getAllLoads(String shipperId, String truckType, String status, int page, int size) {
        LoadStatus loadStatus = status != null ? LoadStatus.valueOf(status.toUpperCase()) : null;

        return loadRepository
                .findByShipperIdContainingAndTruckTypeContainingAndStatus(
                        shipperId == null ? "" : shipperId,
                        truckType == null ? "" : truckType,
                        loadStatus == null ? LoadStatus.POSTED : loadStatus,
                        PageRequest.of(page, size)
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoadResponse updateLoad(UUID id, LoadRequest request) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Load not found"));

        BeanUtils.copyProperties(request, load, "id", "status", "datePosted");
        return mapToResponse(loadRepository.save(load));
    }

    @Override
    public void deleteLoad(UUID id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Load not found"));

        load.setStatus(LoadStatus.CANCELLED);
        loadRepository.save(load);
    }

    private LoadResponse mapToResponse(Load load) {
        LoadResponse response = new LoadResponse();
        BeanUtils.copyProperties(load, response);
        return response;
    }
}
