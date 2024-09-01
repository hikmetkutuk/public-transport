package com.route.service;

import com.route.dto.RouteRequest;
import com.route.dto.RouteResponse;
import com.route.mapper.RouteMapper;
import com.route.repository.RouteRepository;
import com.shared.exception.DataAccessException;
import com.shared.exception.UnexpectedException;
import com.shared.model.Route;
import com.shared.model.Station;
import com.station.dto.StationResponse;
import com.station.mapper.StationMapper;
import com.station.service.StationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final StationMapper stationMapper;
    private final StationService stationService;

    @PersistenceContext
    private final EntityManager entityManager;

    public RouteService(
            RouteRepository routeRepository,
            RouteMapper routeMapper,
            StationMapper stationMapper,
            StationService stationService,
            EntityManager entityManager) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.stationMapper = stationMapper;
        this.stationService = stationService;
        this.entityManager = entityManager;
    }

    @Transactional
    public ResponseEntity<RouteResponse> createRoute(RouteRequest routeRequest) {
        try {
            Route route = routeMapper.toRoute(routeRequest);
            route = routeRepository.save(route);
            return ResponseEntity.ok(routeMapper.fromRoute(route));
        } catch (Exception e) {
            log.error("Failed to create route", e);
            throw e;
        }
    }

    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        try {
            var routes = routeRepository.findAll().stream()
                    .map(routeMapper::fromRoute)
                    .toList();

            log.info("Routes retrieved successfully: {}", routes);
            return ResponseEntity.ok(routes);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing route data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while retrieving route data: " + e.getMessage());
        }
    }

    public ResponseEntity<RouteResponse> getRouteById(Long id) {
        try {
            var route = routeRepository
                    .findById(id)
                    .orElseThrow(() -> new DataAccessException("Route not found with id: " + id));
            log.info("Route retrieved successfully: {}", route);
            return ResponseEntity.ok(routeMapper.fromRoute(route));
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing route data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while retrieving route data: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<RouteResponse> updateRoute(RouteRequest routeRequest, Long id) {
        try {
            var route = routeRepository
                    .findById(id)
                    .orElseThrow(() -> new DataAccessException("Route not found with id: " + id));

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

            route.setStartStation(startStation);
            route.setEndStation(endStation);
            route.setEstimatedDuration(Math.random());
            route.setLastModifiedDate(LocalDateTime.now());
            route = routeRepository.save(route);
            log.info("Route updated successfully: {}", route);
            return ResponseEntity.ok(routeMapper.fromRoute(route));
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing route data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while updating route data: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteRoute(Long id) {
        try {
            routeRepository.deleteById(id);
            log.info("Route deleted successfully: {}", id);
            return ResponseEntity.ok("Route deleted successfully");
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing route data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while deleting route data: " + e.getMessage());
        }
    }
}
