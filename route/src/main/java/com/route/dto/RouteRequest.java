package com.route.dto;

import jakarta.validation.constraints.NotNull;

public record RouteRequest(
        @NotNull(message = "Start station is mandatory") Long startStationId,
        @NotNull(message = "End station is mandatory") Long endStationId,
        Long vehicleId) {}
