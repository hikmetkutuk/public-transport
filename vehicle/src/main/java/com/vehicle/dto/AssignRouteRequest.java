package com.vehicle.dto;

import jakarta.validation.constraints.NotNull;

public record AssignRouteRequest(
        @NotNull(message = "Vehicle id is mandatory") Long vehicleId,
        @NotNull(message = "Route id is mandatory") Long routeId) {}
