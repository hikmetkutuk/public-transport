package com.station.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shared.helper.CustomDoubleDeserializer;
import jakarta.validation.constraints.NotNull;

public record StationRequest(
        @NotNull(message = "Longitude is mandatory") @JsonDeserialize(using = CustomDoubleDeserializer.class)
                double longitude,
        @NotNull(message = "Latitude is mandatory") @JsonDeserialize(using = CustomDoubleDeserializer.class)
                double latitude,
        @NotNull(message = "Elevation is mandatory") @JsonDeserialize(using = CustomDoubleDeserializer.class)
                double elevation,
        @NotNull(message = "Number is mandatory") int number,
        String name) {}
