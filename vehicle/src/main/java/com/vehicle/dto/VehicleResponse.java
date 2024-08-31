package com.vehicle.dto;

import java.time.LocalDateTime;

public record VehicleResponse(
        Long id,
        String plate,
        String model,
        String color,
        String status,
        int capacity,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {}
