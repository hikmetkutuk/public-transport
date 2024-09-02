package com.vehicle.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.route.repository.RouteRepository;
import com.shared.exception.UnexpectedException;
import com.shared.model.Route;
import com.shared.model.Vehicle;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {VehicleService.class, VehicleRepository.class, RouteRepository.class})
public class VehicleServiceIntegrationTest {

    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private VehicleMapper vehicleMapper;

    @MockBean
    private RouteRepository routeRepository;

    private Vehicle vehicle;
    private Route route;

    @BeforeEach
    public void setUp() {
        vehicle = new Vehicle();
        vehicle.setId(1L);

        route = new Route();
        route.setId(1L);
    }

    @Test
    public void shouldAssignRouteToVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        Route route = new Route();
        route.setId(1L);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));

        VehicleResponse vehicleResponse = new VehicleResponse(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getStatus(),
                vehicle.getCapacity(),
                route,
                LocalDateTime.now(),
                LocalDateTime.now());
        when(vehicleMapper.fromVehicle(any(Vehicle.class))).thenReturn(vehicleResponse);

        ResponseEntity<VehicleResponse> response = vehicleService.assignRouteToVehicle(1L, 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldThrowUnexpectedExceptionWhenVehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.assignRouteToVehicle(1L, 1L))
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining(
                        "Unexpected error occurred while assigning route to vehicle: Vehicle not found with id: 1");
    }
}
