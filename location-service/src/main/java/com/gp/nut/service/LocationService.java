package com.gp.nut.service;

import com.gp.nut.dto.LocationCreateRequestDTO;
import com.gp.nut.dto.LocationResponseDTO;
import com.gp.nut.dto.LocationUpdateRequestDTO;

import java.util.List;

public interface LocationService {

    // 장소 등록
    LocationResponseDTO createLocation(LocationCreateRequestDTO request);

    // 전체 조회
    List<LocationResponseDTO> getAllLocations();

    // 수정
    LocationResponseDTO updateLocation(Long id, LocationUpdateRequestDTO request);

    // 삭제
    void deleteLocation(Long id);

    // 스케줄별 조회
    List<LocationResponseDTO> getLocationsBySchedule(Long scheduleId);

    // 랜덤 선택
    LocationResponseDTO getRandomLocation(Long scheduleId);
}