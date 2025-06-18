package com.gp.nut.service;

import com.gp.nut.dto.LocationCreateRequestDTO;
import com.gp.nut.dto.LocationResponseDTO;
import com.gp.nut.dto.LocationUpdateRequestDTO;
import com.gp.nut.entity.Location;
import com.gp.nut.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    /**
     * 장소 등록 메서드
     * 1. 요청 DTO를 Entity로 변환
     * 2. DB에 저장 (ID, createdAt 자동 생성)
     * 3. 저장된 Entity를 ResponseDTO로 변환하여 반환
     */
    @Override
    public LocationResponseDTO createLocation(LocationCreateRequestDTO request) {

        // DTO → Entity 변환 (DB 저장을 위해)
        Location location = new Location();
        location.setName(request.getName());
        location.setAddress(request.getAddress());
        location.setPriceRange(request.getPriceRange());
        location.setDescription(request.getDescription());
        location.setScheduleId(request.getScheduleId());
        location.setRegisteredBy(request.getRegisteredBy());

        // 데이터베이스에 저장
        Location savedLocation = locationRepository.save(location);

        // 저장된 Entity의 모든 정보를 응답용 DTO로 변환(클라이언트 응답용)
        LocationResponseDTO response= new LocationResponseDTO();
        response.setId(savedLocation.getId());
        response.setName(savedLocation.getName());
        response.setAddress(savedLocation.getAddress());
        response.setPriceRange(savedLocation.getPriceRange());
        response.setDescription(savedLocation.getDescription());
        response.setScheduleId(savedLocation.getScheduleId());
        response.setRegisteredBy(savedLocation.getRegisteredBy());
        response.setCreatedAt(savedLocation.getCreatedAt());

        return response; // 클라이언트에게 전달
    }

    /**
     * 전체 장소 목록 조회
     */
    @Override
    public List<LocationResponseDTO> getAllLocations() {

        List<Location> locations = locationRepository.findAll();

        return locations.stream()
                .map(location -> {
                    LocationResponseDTO dto = new LocationResponseDTO();
                    dto.setId(location.getId());
                    dto.setName(location.getName());
                    dto.setAddress(location.getAddress());
                    dto.setPriceRange(location.getPriceRange());
                    dto.setDescription(location.getDescription());
                    dto.setScheduleId(location.getScheduleId());
                    dto.setRegisteredBy(location.getRegisteredBy());
                    dto.setCreatedAt(location.getCreatedAt());
                    return dto;
                })
                .toList();
    }

    @Override
    public LocationResponseDTO updateLocation(Long id, LocationUpdateRequestDTO request) {

        // 기존 엔티티 조회 (id로 찾기)
        Location existingLocation = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다."));

        // 기존 엔티티의 필드만 수정 (DTO → Entity 부분 변환)
        existingLocation.setName(request.getName());
        existingLocation.setAddress(request.getAddress());
        existingLocation.setPriceRange(request.getPriceRange());
        existingLocation.setDescription(request.getDescription());

        // 저장
        Location savedLocation = locationRepository.save(existingLocation);

        LocationResponseDTO response= new LocationResponseDTO();
        response.setId(savedLocation.getId());
        response.setName(savedLocation.getName());
        response.setAddress(savedLocation.getAddress());
        response.setPriceRange(savedLocation.getPriceRange());
        response.setDescription(savedLocation.getDescription());
        response.setScheduleId(savedLocation.getScheduleId());
        response.setRegisteredBy(savedLocation.getRegisteredBy());
        response.setCreatedAt(savedLocation.getCreatedAt());

        return response;
    }

    @Override
    public void deleteLocation(Long id) {
        // 존재하는지 먼저 확인
        Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("삭제할 장소를 찾을 수 없습니다."));

        // 삭제 실행
        locationRepository.delete(location);
    }

    /**
     * 특정 스케줄의 장소들 조회
     */
    @Override
    public List<LocationResponseDTO> getLocationsBySchedule(Long scheduleId) {
        // 특정 스케줄의 장소들만 조회
        List<Location> locations = locationRepository.findByScheduleId(scheduleId);

        //  Entity List → ResponseDTO List 변환
        return locations.stream().map(location -> {
            LocationResponseDTO dto = new LocationResponseDTO();
            dto.setId(location.getId());
            dto.setName(location.getName());
            dto.setAddress(location.getAddress());
            dto.setPriceRange(location.getPriceRange());
            dto.setDescription(location.getDescription());
            dto.setScheduleId(location.getScheduleId());
            dto.setRegisteredBy(location.getRegisteredBy());
            dto.setCreatedAt(location.getCreatedAt());
            return dto;
        })
                .toList();
    }

    // SecureRandom을 클래스 레벨에서 한 번만 생성 (성능 최적화)
    // 의사랜덤(Math.random)이 아닌 암호학적으로 안전한 진짜 랜덤 사용
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public LocationResponseDTO getRandomLocation(Long scheduleId) {

        // 해당 스케줄의 모든 장소 조회
        List<Location> locations = locationRepository.findByScheduleId(scheduleId);

        // 장소가 없으면 예외
        if(locations.isEmpty()) {
            throw new RuntimeException("해당 스케줄에 등록된 장소가 없습니다.");
        }
        // SecureRandom 사용으로 진짜 랜덤 구현
        Location randomLocation = locations.get(secureRandom.nextInt(locations.size()));

        LocationResponseDTO response= new LocationResponseDTO();
        response.setId(randomLocation.getId());
        response.setName(randomLocation.getName());
        response.setAddress(randomLocation.getAddress());
        response.setPriceRange(randomLocation.getPriceRange());
        response.setDescription(randomLocation.getDescription());
        response.setScheduleId(randomLocation.getScheduleId());
        response.setRegisteredBy(randomLocation.getRegisteredBy());
        response.setCreatedAt(randomLocation.getCreatedAt());

        return response;
    }
}
