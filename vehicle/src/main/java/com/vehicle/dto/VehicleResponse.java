package com.vehicle.dto;

public record VehicleResponse(Long id, String plate, String model, String color, String status, int capacity) {}
