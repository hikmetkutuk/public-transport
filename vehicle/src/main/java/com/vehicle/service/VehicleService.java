package com.vehicle.service;

import com.vehicle.dto.VehicleRequest;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.exception.VehicleCreationException;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.repository.VehicleRepository;
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
            throw new VehicleCreationException(
                    "Data integrity violation occurred while creating vehicle: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating vehicle: " + e.getMessage());
            throw new VehicleCreationException("Unexpected error occurred while creating vehicle: " + e.getMessage());
        }
    }
}
