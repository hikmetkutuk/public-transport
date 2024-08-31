package com.station.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shared.helper.CustomDoubleDeserializer;

public record StationRequest(
        @JsonDeserialize(using = CustomDoubleDeserializer.class) double longitude,
        @JsonDeserialize(using = CustomDoubleDeserializer.class) double latitude,
        @JsonDeserialize(using = CustomDoubleDeserializer.class) double elevation,
        int number,
        String name) {}
