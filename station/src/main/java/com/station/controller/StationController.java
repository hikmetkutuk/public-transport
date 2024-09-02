package com.station.controller;

import com.station.dto.StationRequest;
import com.station.dto.StationResponse;
import com.station.service.StationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/station")
@Tag(name = "Station", description = "Station management API")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/add")
    public ResponseEntity<StationResponse> addStation(@Valid @RequestBody StationRequest request) {
        return stationService.addStation(request);
    }

    @GetMapping("/list")
    public ResponseEntity<List<StationResponse>> getAllStations() {
        return stationService.getAllStations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationResponse> getStationById(@PathVariable Long id) {
        return stationService.getStationById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StationResponse> updateStation(
            @PathVariable Long id, @Valid @RequestBody StationRequest request) {
        return stationService.updateStation(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStation(@PathVariable Long id) {
        return stationService.deleteStation(id);
    }
}
