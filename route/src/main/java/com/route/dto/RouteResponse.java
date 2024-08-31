package com.route.dto;

import com.station.dto.StationResponse;
import com.vehicle.model.Vehicle;
import java.time.LocalDateTime;

public record RouteResponse(
        Long id,
        StationResponse startStation,
        StationResponse endStation,
        Vehicle vehicle,
        Double estimatedDuration,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {}
