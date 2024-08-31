package com.route.mapper;

import com.route.dto.RouteRequest;
import com.route.dto.RouteResponse;
import com.route.model.Route;
import com.station.dto.StationResponse;
import com.station.mapper.StationMapper;
import com.station.model.Station;
import com.station.service.StationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class RouteMapper {
    private final StationMapper stationMapper;
    private final StationService stationService;

    @PersistenceContext
    private EntityManager entityManager;

    public RouteMapper(StationMapper stationMapper, StationService stationService) {
        this.stationMapper = stationMapper;
        this.stationService = stationService;
    }

    public RouteResponse fromRoute(Route route) {
        Station startStation = route.getStartStation();
        Station endStation = route.getEndStation();
        return new RouteResponse(
                route.getId(),
                stationMapper.fromStation(startStation),
                stationMapper.fromStation(endStation),
                route.getVehicle(),
                route.getEstimatedDuration(),
                route.getCreatedDate(),
                route.getLastModifiedDate());
    }

    public Route toRoute(RouteRequest routeRequest) {
        if (routeRequest == null) {
            return null;
        }

        StationResponse startStationResponse =
                stationService.getStationById(routeRequest.startStationId()).getBody();
        if (startStationResponse == null) {
            throw new EntityNotFoundException("Start station not found");
        }

        StationResponse endStationResponse =
                stationService.getStationById(routeRequest.endStationId()).getBody();
        if (endStationResponse == null) {
            throw new EntityNotFoundException("End station not found");
        }

        Station startStation = stationMapper.toStation(startStationResponse);
        Station endStation = stationMapper.toStation(endStationResponse);

        startStation = entityManager.merge(startStation);
        endStation = entityManager.merge(endStation);

        return Route.builder()
                .startStation(startStation)
                .endStation(endStation)
                .estimatedDuration(Math.random())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }
}
