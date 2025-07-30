package com.cargopro.loadbooking.controller;

import com.cargopro.loadbooking.dto.LoadRequest;
import com.cargopro.loadbooking.dto.LoadResponse;
import com.cargopro.loadbooking.service.LoadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/load")
@RequiredArgsConstructor
@Tag(name = "Load Management", description = "Endpoints for load operations")
public class LoadController {

    private final LoadService loadService;

    @Operation(summary = "Create a new load")
    @ApiResponse(responseCode = "200", description = "Load created successfully")
    @PostMapping
    public ResponseEntity<LoadResponse> createLoad(@RequestBody LoadRequest request) {
        return ResponseEntity.ok(loadService.createLoad(request));
    }

    @Operation(summary = "Get all loads with filters and pagination")
    @ApiResponse(responseCode = "200", description = "List of loads")
    @GetMapping
    public ResponseEntity<List<LoadResponse>> getAllLoads(
            @RequestParam(required = false) String shipperId,
            @RequestParam(required = false) String truckType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(loadService.getAllLoads(shipperId, truckType, status, page, size));
    }

    @Operation(summary = "Get load by ID")
    @ApiResponse(responseCode = "200", description = "Load found")
    @GetMapping("/{id}")
    public ResponseEntity<LoadResponse> getLoad(@PathVariable UUID id) {
        return ResponseEntity.ok(loadService.getLoadById(id));
    }

    @Operation(summary = "Update load by ID")
    @ApiResponse(responseCode = "200", description = "Load updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<LoadResponse> updateLoad(@PathVariable UUID id, @RequestBody LoadRequest request) {
        return ResponseEntity.ok(loadService.updateLoad(id, request));
    }

    @Operation(summary = "Delete load by ID")
    @ApiResponse(responseCode = "200", description = "Load deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoad(@PathVariable UUID id) {
        loadService.deleteLoad(id);
        return ResponseEntity.ok().build();
    }
}
