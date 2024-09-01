package com.route.dto;

import com.shared.model.Vehicle;
import com.station.dto.StationResponse;
import java.time.LocalDateTime;
import java.util.List;

public record RouteResponse(
        Long id,
        StationResponse startStation,
        StationResponse endStation,
        List<Vehicle> vehicle,
        Double estimatedDuration,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {}
