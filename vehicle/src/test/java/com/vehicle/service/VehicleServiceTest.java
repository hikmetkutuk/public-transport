package com.vehicle.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.route.repository.RouteRepository;
import com.shared.exception.DataAccessException;
import com.shared.model.Route;
import com.shared.model.Vehicle;
import com.vehicle.dto.VehicleResponse;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private VehicleMapper mapper;

    private Vehicle vehicle;
    private Route route;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicle = new Vehicle();
        vehicle.setId(1L);

        route = new Route();
        route.setId(1L);
    }

    @Test
    public void shouldAssignRouteToVehicleSuccessfully() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(routeRepository.save(route)).thenReturn(route);
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
        when(mapper.fromVehicle(vehicle)).thenReturn(vehicleResponse);

        ResponseEntity<VehicleResponse> response = vehicleService.assignRouteToVehicle(1L, 1L);

        verify(vehicleRepository).save(vehicle);
        verify(routeRepository).save(route);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldHandleDataAccessException() {
        when(vehicleRepository.findById(1L)).thenThrow(DataAccessException.class);

        assertThatThrownBy(() -> vehicleService.assignRouteToVehicle(1L, 1L))
                .isInstanceOf(DataAccessException.class)
                .hasMessageContaining("Error accessing data from database");
    }
}
