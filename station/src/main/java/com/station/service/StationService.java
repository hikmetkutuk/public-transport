package com.station.service;

import com.shared.exception.DataAccessException;
import com.shared.exception.DataCreationException;
import com.shared.exception.DataNotFoundException;
import com.shared.exception.UnexpectedException;
import com.station.dto.StationRequest;
import com.station.dto.StationResponse;
import com.station.mapper.StationMapper;
import com.station.repository.StationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StationService {
    private final StationRepository stationRepository;
    private final StationMapper mapper;

    public StationService(StationRepository stationRepository, StationMapper mapper) {
        this.stationRepository = stationRepository;
        this.mapper = mapper;
    }

    public ResponseEntity<StationResponse> addStation(StationRequest request) {
        try {
            var newStation = stationRepository.save(mapper.toStation(request));
            log.info("Station created successfully with id: {}", newStation.getId());
            return ResponseEntity.ok(mapper.fromStation(newStation));
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while creating station: " + e.getMessage());
            throw new DataCreationException(
                    "Data integrity violation occurred while creating station: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating station: " + e.getMessage());
            throw new DataCreationException("Unexpected error occurred while creating station: " + e.getMessage());
        }
    }

    public ResponseEntity<List<StationResponse>> getAllStations() {
        try {
            var stations = stationRepository.findAll().stream()
                    .map(mapper::fromStation)
                    .toList();
            log.info("Stations retrieved successfully: {}", stations);
            return ResponseEntity.ok(stations);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while retrieving station data: " + e.getMessage());
        }
    }

    public ResponseEntity<StationResponse> getStationById(Long id) {
        try {
            var station = stationRepository
                    .findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Station not found with id: " + id));
            log.info("Station retrieved successfully: {}", station);
            return ResponseEntity.ok(mapper.fromStation(station));
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while retrieving station data: " + e.getMessage());
        }
    }

    public ResponseEntity<StationResponse> updateStation(Long id, StationRequest request) {
        try {
            var station = stationRepository
                    .findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Station not found with id: " + id));

            station.setLatitude(request.latitude());
            station.setLongitude(request.longitude());
            station.setElevation(request.elevation());
            station.setNumber(request.number());
            station.setName(request.name());
            station.setLastModifiedDate(LocalDateTime.now());
            var updatedStation = stationRepository.save(station);

            stationRepository.save(updatedStation);
            log.info("Station updated successfully with id: {}", updatedStation.getId());
            return ResponseEntity.ok(mapper.fromStation(updatedStation));
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while updating station data: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteStation(Long id) {
        try {
            var station = stationRepository
                    .findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Station not found with id: " + id));
            stationRepository.delete(station);
            log.info("Station deleted successfully with id: {}", id);
            return ResponseEntity.ok("Station deleted successfully with id: " + id);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while deleting station data: " + e.getMessage());
        }
    }
}
