package com.station.mapper;

import com.station.dto.StationRequest;
import com.station.dto.StationResponse;
import com.station.model.Station;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class StationMapper {
    public StationResponse fromStation(Station station) {
        return new StationResponse(
                station.getId(),
                station.getLongitude(),
                station.getLatitude(),
                station.getElevation(),
                station.getNumber(),
                station.getName(),
                station.getCreatedDate(),
                station.getLastModifiedDate());
    }

    public Station toStation(StationRequest stationRequest) {
        if (stationRequest == null) {
            return null;
        }
        return Station.builder()
                .longitude(stationRequest.longitude())
                .latitude(stationRequest.latitude())
                .elevation(stationRequest.elevation())
                .number(stationRequest.number())
                .name(stationRequest.name())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public Station toStation(StationResponse stationResponse) {
        if (stationResponse == null) {
            return null;
        }
        return Station.builder()
                .id(stationResponse.id())
                .longitude(stationResponse.longitude())
                .latitude(stationResponse.latitude())
                .elevation(stationResponse.elevation())
                .number(stationResponse.number())
                .name(stationResponse.name())
                .createdDate(stationResponse.createdDate())
                .lastModifiedDate(stationResponse.lastModifiedDate())
                .build();
    }
}
