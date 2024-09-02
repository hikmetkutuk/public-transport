package com.vehicle.dto;

import jakarta.validation.constraints.NotBlank;

public record VehicleRequest(
        @NotBlank(message = "Plate is mandatory") String plate,
        String model,
        String color,
        String status,
        int capacity) {}
