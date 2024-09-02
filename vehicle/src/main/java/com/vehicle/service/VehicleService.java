package com.vehicle.service;

import com.route.repository.RouteRepository;
import com.shared.exception.DataAccessException;
import com.shared.exception.DataCreationException;
import com.shared.exception.DataNotFoundException;
import com.shared.exception.UnexpectedException;
import com.shared.model.Route;
import com.shared.model.Vehicle;
import com.vehicle.dto.VehicleRequest;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final RouteRepository routeRepository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository vehicleRepository, RouteRepository routeRepository, VehicleMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.routeRepository = routeRepository;
        this.mapper = mapper;
    }

    public VehicleResponse addVehicle(VehicleRequest request) {
        try {
            var newVehicle = vehicleRepository.save(mapper.toVehicle(request));
            log.info("Vehicle created successfully with id: {}", newVehicle.getId());
            return mapper.fromVehicle(newVehicle);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while creating vehicle: " + e.getMessage());
            throw new DataCreationException(
                    "Data integrity violation occurred while creating vehicle: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating vehicle: " + e.getMessage());
            throw new DataCreationException("Unexpected error occurred while creating vehicle: " + e.getMessage());
        }
    }

    public List<VehicleResponse> getAllVehicles() {
        try {
            var vehicles = vehicleRepository.findAll().stream()
                    .map(mapper::fromVehicle)
                    .toList();
            log.info("Vehicles retrieved successfully");
            return vehicles;
        } catch (DataAccessException e) {
            log.error("Error accessing data from database: " + e.getMessage());
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving vehicle data: " + e.getMessage());
            throw new UnexpectedException("Unexpected error occurred while retrieving vehicle data: " + e.getMessage());
        }
    }

    public VehicleResponse getVehicleById(Long id) {
        try {
            var vehicle = vehicleRepository
                    .findById(id)
                    .map(mapper::fromVehicle)
                    .orElseThrow(() -> new DataAccessException("Vehicle not found with id: " + id));
            log.info("Vehicle retrieved successfully: {}", vehicle.id());
            return vehicle;
        } catch (DataAccessException e) {
            log.error("Error accessing data from database: " + e.getMessage());
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving vehicle data: " + e.getMessage());
            throw new UnexpectedException("Unexpected error occurred while retrieving vehicle data: " + e.getMessage());
        }
    }

    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        try {
            var vehicle = vehicleRepository
                    .findById(id)
                    .orElseThrow(() -> new DataAccessException("Vehicle not found with id: " + id));

            vehicle.setPlate(request.plate());
            vehicle.setModel(request.model());
            vehicle.setColor(request.color());
            vehicle.setStatus(request.status());
            vehicle.setCapacity(request.capacity());
            vehicle.setLastModifiedDate(LocalDateTime.now());

            var updatedVehicle = vehicleRepository.save(vehicle);
            log.info("Vehicle updated successfully: {}", updatedVehicle.getId());
            return mapper.fromVehicle(updatedVehicle);
        } catch (DataAccessException e) {
            log.error("Error accessing data from database: " + e.getMessage());
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating vehicle data: " + e.getMessage());
            throw new UnexpectedException("Unexpected error occurred while updating vehicle data: " + e.getMessage());
        }
    }

    public String deleteVehicle(Long id) {
        try {
            if (!vehicleRepository.existsById(id)) {
                log.error("Vehicle not found in database with id: {}", id);
                throw new DataNotFoundException("Vehicle not found in database with id: " + id);
            }
            vehicleRepository.deleteById(id);
            log.info("Vehicle deleted successfully with id: {}", id);
            return "Vehicle deleted successfully with id: " + id;
        } catch (DataAccessException e) {
            log.error("Error accessing data from database: " + e.getMessage());
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting vehicle data: " + e.getMessage());
            throw new UnexpectedException("Unexpected error occurred while deleting vehicle data: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<VehicleResponse> assignRouteToVehicle(Long vehicleId, Long routeId) {
        try {
            Vehicle vehicle = vehicleRepository
                    .findById(vehicleId)
                    .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + vehicleId));
            Route route = routeRepository
                    .findById(routeId)
                    .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + routeId));

            if (vehicle.getRoute() != null) {
                Route currentRoute = vehicle.getRoute();
                currentRoute.getVehicles().remove(vehicle);
                routeRepository.save(currentRoute);
            }

            vehicle.setRoute(route);
            route.getVehicles().add(vehicle);

            vehicleRepository.save(vehicle);
            routeRepository.save(route);

            log.info("Route {} assigned to vehicle {} successfully", route.getId(), vehicle.getId());
            return ResponseEntity.ok(mapper.fromVehicle(vehicle));
        } catch (DataAccessException e) {
            log.error("Error accessing data from database: " + e.getMessage());
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while assigning route to vehicle: " + e.getMessage());
            throw new UnexpectedException(
                    "Unexpected error occurred while assigning route to vehicle: " + e.getMessage());
        }
    }
}
