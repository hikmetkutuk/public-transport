package com.route.controller;

import com.route.dto.RouteRequest;
import com.route.dto.RouteResponse;
import com.route.service.RouteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/add")
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RouteRequest routeRequest) {
        return routeService.createRoute(routeRequest);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Long id) {
        return routeService.getRouteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RouteResponse> updateRoute(
            @Valid @RequestBody RouteRequest routeRequest, @PathVariable Long id) {
        return routeService.updateRoute(routeRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
        return routeService.deleteRoute(id);
    }
}
