package com.vehicle.mapper;

import com.vehicle.dto.VehicleRequest;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.model.Vehicle;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapper {
    public VehicleResponse fromVehicle(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getStatus(),
                vehicle.getCapacity(),
                vehicle.getCreatedDate(),
                vehicle.getLastModifiedDate());
    }

    public Vehicle toVehicle(VehicleRequest vehicleRequest) {
        if (vehicleRequest == null) {
            return null;
        }
        return Vehicle.builder()
                .plate(vehicleRequest.plate())
                .model(vehicleRequest.model())
                .color(vehicleRequest.color())
                .status(vehicleRequest.status())
                .capacity(vehicleRequest.capacity())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }
}
