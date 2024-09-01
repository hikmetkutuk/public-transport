package com.vehicle.controller;

import com.vehicle.dto.AssignRouteRequest;
import com.vehicle.dto.VehicleRequest;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public ResponseEntity<VehicleResponse> addVehicle(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.addVehicle(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.deleteVehicle(id));
    }

    @PostMapping("/assign-route")
    public ResponseEntity<VehicleResponse> assignRouteToVehicle(@RequestBody AssignRouteRequest request) {
        return vehicleService.assignRouteToVehicle(request.vehicleId(), request.routeId());
    }
}
