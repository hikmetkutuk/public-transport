package com.vehicle.dto;

import com.shared.model.Route;
import java.time.LocalDateTime;

public record VehicleResponse(
        Long id,
        String plate,
        String model,
        String color,
        String status,
        int capacity,
        Route route,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {}
