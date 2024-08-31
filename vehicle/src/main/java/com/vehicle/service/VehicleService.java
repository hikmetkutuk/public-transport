package com.vehicle.service;

import com.shared.exception.DataAccessException;
import com.shared.exception.DataCreationException;
import com.shared.exception.DataNotFoundException;
import com.shared.exception.UnexpectedException;
import com.vehicle.dto.VehicleRequest;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper mapper) {
        this.vehicleRepository = vehicleRepository;
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
            log.info("Vehicles retrieved successfully: {}", vehicles);
            return vehicles;
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while retrieving vehicle data: " + e.getMessage());
        }
    }

    public VehicleResponse getVehicleById(Long id) {
        try {
            var vehicle = vehicleRepository
                    .findById(id)
                    .map(mapper::fromVehicle)
                    .orElseThrow(() -> new DataAccessException("Vehicle not found with id: " + id));
            log.info("Vehicle retrieved successfully: {}", vehicle);
            return vehicle;
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
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
            log.info("Vehicle updated successfully: {}", updatedVehicle);
            return mapper.fromVehicle(updatedVehicle);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
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
            throw new DataAccessException("Error accessing data from database: " + e.getMessage());
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected error occurred while deleting vehicle data: " + e.getMessage());
        }
    }
}
