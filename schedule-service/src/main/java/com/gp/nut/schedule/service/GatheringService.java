package com.gp.nut.schedule.service;

import com.gp.nut.schedule.dto.GatheringRequestDto;
import com.gp.nut.schedule.dto.GatheringResponseDto;
import com.gp.nut.schedule.dto.UpdateConfirmedLocationDto;
import com.gp.nut.schedule.dto.UpdateDateRequestDto;
import com.gp.nut.schedule.dto.UpdateReviewDto;
import com.gp.nut.schedule.entity.Gathering;
import com.gp.nut.schedule.repository.GatheringRepository;
import com.gp.nut.schedule.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import java.security.Security;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/* 서비스는 비즈니스 로직을 담당한다.
 * entity -> response/resquest 또는 그 반대로 변환하는 로직을 포함한다.
 * db에 직접 접근하는 로직이 포함되므로 @Transaction어노테이션은 서비스에 붙인다.
 * 단순조회는 생략이 가능하고, 읽기 전용이면 @Transcational(readOnly = true)를 붙인다. */

@Service
@RequiredArgsConstructor
@Slf4j
public class GatheringService {

  private final GatheringRepository gatheringRepository;

  // 타겟 유저가 권한이 있는지 확인하는 메서드 (boss 본인과 ADMIN일시에만 true)
  private boolean hasPermission(Long targetUserId) {
    String currentUserId = SecurityUtil.getCurrentUserId();
    boolean isOwner = targetUserId.equals(Long.valueOf(currentUserId));
    boolean isAdmin = SecurityUtil.getCurrentUserRoles().stream().anyMatch("ADMIN"::equals);
    return isOwner || isAdmin;
  }

  // 참여자 목록과 사장id를 받아 current유저가 포함되어 있는지 확인
  private boolean hasReviewPermission(List<Long> participantIds, Long bossId) {
    if (SecurityUtil.getCurrentUserRoles().stream().anyMatch("ADMIN"::equals)) {
      return true; // 관리자면 리뷰 작성 가능
    }

    log.info(participantIds.toString());
    log.info(bossId.toString());

    Long currentUserId = Long.valueOf(SecurityUtil.getCurrentUserId());
    boolean isParticipant = participantIds.contains(currentUserId); // 참여자이거나
    boolean isBoss = bossId.equals(currentUserId); // 주최 사장이면
    return isParticipant || isBoss; // 회식 참여자이거나, 주최 보스면 리뷰 작성 가능
  }

  // Gathering 등록(보스, 날짜, 참여사원, 회식장소 후보들)
  @Transactional
  public GatheringResponseDto createGathering(GatheringRequestDto requestDto) {
    if (!hasPermission(requestDto.getBossId())) {
      throw new AccessDeniedException("회식 등록 권한이 없습니다. (사장 본인과 관리자만 가능합니다.)");
    }

    Gathering savedGathering = gatheringRepository.save(requestDto.toGathering());
    return savedGathering.toResponseDto();
  }

  // Gathering 날짜 변경
  @Transactional
  public GatheringResponseDto updateGatheringDate(UpdateDateRequestDto requestDto) {

    Gathering retrieveGathering = gatheringRepository.findById(requestDto.getId()).orElseThrow(
        // EntityNotFoundException: 존재하지 않는 자원 처리
        // IllegalArgumentException(잘못된 인자, 존재하지 않는 자원 요청) 보다 더 직관적
        () -> new EntityNotFoundException(
            "해당 회식은 존재하지 않습니다. ID: " + requestDto.getId()));

    if (!hasPermission(retrieveGathering.getBossId())) {
      throw new AccessDeniedException("회식 날짜 수정 권한이 없습니다. (본인과 관리자만 가능합니다.)");
    }

    retrieveGathering.updateDate(requestDto.getDate()); // 찾은 회식의 날짜 변경
    Gathering updatedGathering = gatheringRepository.save(retrieveGathering); // db 반영
    return updatedGathering.toResponseDto();
  }

  // Gathering에 확정된 회식정보 저장
  @Transactional
  public GatheringResponseDto updateConfirmedLocation(UpdateConfirmedLocationDto requestDto) {
    Gathering retrieveGathering = gatheringRepository.findById(requestDto.getId()).orElseThrow(
        () -> new EntityNotFoundException(
            "해당 회식은 존재하지 않습니다. ID: " + requestDto.getId()
        )
    );

    if (!hasPermission(retrieveGathering.getBossId())) {
      throw new AccessDeniedException("회식 확정 장소 수정 권한이 없습니다. (본인과 관리자만 가능합니다.)");
    }

    retrieveGathering.updateConfirmedLocationId(requestDto.getConfirmedLocationId());
    Gathering updatedGathering = gatheringRepository.save(retrieveGathering);
    return updatedGathering.toResponseDto();
  }

  // Gathering에 리뷰
  @Transactional
  public GatheringResponseDto updateReviewIds(UpdateReviewDto requestDto) {
    Gathering retrieveGathering = gatheringRepository.findById(requestDto.getId()).orElseThrow(
        () -> new EntityNotFoundException("해당 회식을 찾을 수 없습니다: ID: " + requestDto.getId())
    );
    List<Long> participantIds = retrieveGathering.getParticipantIds();
    Long bossId = retrieveGathering.getBossId();

    if (!hasReviewPermission(participantIds, retrieveGathering.getBossId())) { // 참여자 목록과 사장id를 받아 current유저가 포함되어 있는지 확인
      throw new AccessDeniedException("회식 리뷰 작성 권한이 없습니다. (회식 참여자(사원, 사장)와 관리자만 가능합니다.)");
    }

    retrieveGathering.addReviewId(requestDto.getReviewId());
    Gathering updatedGathering = gatheringRepository.save(retrieveGathering);
    return updatedGathering.toResponseDto();
  }


  // Gathering 삭제
  @Transactional
  public void deleteGathering(Long id) {
    Gathering retrieveGathering = gatheringRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("회식을 찾을 수 없습니다."));

    if (!hasPermission(retrieveGathering.getBossId())) {
      throw new AccessDeniedException("회식 삭제 권한이 없습니다. (본인과 관리자만 가능합니다.)");
    }

    gatheringRepository.delete(retrieveGathering);
    log.info("회식 삭제됨 - ID: {}, 삭제자: {}", id, SecurityUtil.getCurrentUserId());
  }

  // 모든 회식 정보 조회하기
  @Transactional(readOnly = true)
  public List<GatheringResponseDto> findAllGatherings() {
    List<GatheringResponseDto> gatheringList =
        gatheringRepository.findAll().stream().map(Gathering::toResponseDto).toList();
    return gatheringList;
  }

  // 회식 id로 회식 정보 조회하기
  @Transactional(readOnly = true)
  public GatheringResponseDto findById(Long id) {
    Gathering retrieveGathering = gatheringRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("해당 회식은 존재하지 않습니다. ID: " + id)
    );
    return retrieveGathering.toResponseDto();
  }

  // 날짜로 회식 정보 조회하기
  @Transactional(readOnly = true)
  public List<GatheringResponseDto> findByDate(LocalDate date) {
    List<GatheringResponseDto> gatheringList = gatheringRepository.findGatheringByDate(date)
        .stream().map(Gathering::toResponseDto).toList();
    return gatheringList;
  }

  // 만든 사장으로 회식 정보 조회하기
  public List<GatheringResponseDto> findByBossId(Long bossId) {
    List<GatheringResponseDto> gatheringList = gatheringRepository.findGatheringByBossId(bossId)
        .stream().map(Gathering::toResponseDto).toList();
    return gatheringList;
  }

}
