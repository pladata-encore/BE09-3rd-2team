package com.gp.nut.controller;

import com.gp.nut.dto.LocationCreateRequestDTO;
import com.gp.nut.dto.LocationResponseDTO;
import com.gp.nut.dto.LocationUpdateRequestDTO;
import com.gp.nut.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Location Service REST API Controller
 * JWT 인증 필요 (EMPLOYEE 권한)
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    /**
     * 서비스 연결 테스트용
     */
    @GetMapping("/test")
    public String test() {
        return "Hello from Location Service!";
    }


    @PostMapping("/create")
    public LocationResponseDTO createLocation(@RequestBody LocationCreateRequestDTO request) {
        return locationService.createLocation(request);
    }

    @GetMapping("/list")
    public List<LocationResponseDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id, @RequestBody LocationUpdateRequestDTO request) {
        LocationResponseDTO response = locationService.updateLocation(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<LocationResponseDTO>> getLocationsByScheduleId(@PathVariable Long scheduleId) {
        List<LocationResponseDTO> locations = locationService.getLocationsBySchedule(scheduleId);
        return ResponseEntity.ok(locations);
    }

    /**
     * 스케줄별 랜덤 장소 선택 (SecureRandom 사용)
     */
    @GetMapping("/schedule/{scheduleId}/random")
    public ResponseEntity<LocationResponseDTO> getRandomLocation(@PathVariable Long scheduleId) {
        LocationResponseDTO response = locationService.getRandomLocation(scheduleId);
        return ResponseEntity.ok(response);
    }
}
