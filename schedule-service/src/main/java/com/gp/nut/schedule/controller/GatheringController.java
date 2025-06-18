package com.gp.nut.schedule.controller;

import com.gp.nut.schedule.dto.GatheringRequestDto;
import com.gp.nut.schedule.dto.GatheringResponseDto;
import com.gp.nut.schedule.dto.UpdateConfirmedLocationDto;
import com.gp.nut.schedule.dto.UpdateDateRequestDto;
import com.gp.nut.schedule.dto.UpdateReviewDto;
import com.gp.nut.schedule.service.GatheringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* 컨트롤러는 외부 요청 수신, 응답 반환한다.
 *  따라서 dto만 갖고있어야한다. */

@Slf4j // 로그 객체 선언
@RestController // springweb 의존성 추가해야함
@RequiredArgsConstructor
@RequestMapping("/gatherings")
@SecurityRequirement(name = "BearerAuth")
public class GatheringController {

  private final GatheringService gatheringService;


  // Gathering 등록(보스, 날짜, 참여사원, 회식장소 후보들)
  @PostMapping
  // 입력데이터의 유효성 검사는 컨트롤러에서 한다. (@Valid)
  public ResponseEntity<GatheringResponseDto> createGathering(
      @RequestBody @Valid GatheringRequestDto requestDto) {
    GatheringResponseDto response = gatheringService.createGathering(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // Gathering 날짜 변경
  @PatchMapping("/{id}/date")
  public ResponseEntity<GatheringResponseDto> updateDate(
      @RequestBody @Valid UpdateDateRequestDto requestDto) {
    // 본인이 만든 회식인지 체크 (구현 필요)
    GatheringResponseDto response = gatheringService.updateGatheringDate(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // Gathering에 확정된 회식정보 저장
  @PatchMapping("/{id}/confirm")
  public ResponseEntity<GatheringResponseDto> updateConfirmedLocation(@RequestBody @Valid
  UpdateConfirmedLocationDto requestDto) {
    GatheringResponseDto response = gatheringService.updateConfirmedLocation(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 회식 리뷰 남기기
  @PatchMapping("{id}/review")
  public ResponseEntity<GatheringResponseDto> updateReview(@RequestBody @Valid UpdateReviewDto requestDto) {
    GatheringResponseDto response = gatheringService.updateReviewIds(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // Gathering 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<GatheringResponseDto> deleteGathering(@PathVariable Long id) {
    gatheringService.deleteGathering(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 삭제는 NO_CONTENT 반환
  }

  // 모든 회식 정보 조회하기
  @GetMapping
  public ResponseEntity<List<GatheringResponseDto>> getAllGatherings() {
    List<GatheringResponseDto> response = gatheringService.findAllGatherings();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 회식 id로 회식 정보 조회하기
  @GetMapping("/{id}")
  public ResponseEntity<GatheringResponseDto> getGatheringsById(@PathVariable Long id) {
    GatheringResponseDto response = gatheringService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 날짜로 회식 정보 조회하기
  @GetMapping("/date")
  public ResponseEntity<List<GatheringResponseDto>> getGatheringsByDate(@RequestParam("date") LocalDate date) {
    List<GatheringResponseDto> response = gatheringService.findByDate(date);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 사장 ID로 회식 조회
  @GetMapping("/boss")
  public List<GatheringResponseDto> getGatheringsByBossId(@RequestParam("bossId") Long bossId) {
    // 변수명과 쿼리파라미명 같으면 @RequestParm 다음 생략 가능
    return gatheringService.findByBossId(bossId);
  }

}
