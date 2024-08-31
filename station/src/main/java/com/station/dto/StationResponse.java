package com.station.dto;

import java.time.LocalDateTime;

public record StationResponse(
        Long id,
        double longitude,
        double latitude,
        double elevation,
        int number,
        String name,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {}
