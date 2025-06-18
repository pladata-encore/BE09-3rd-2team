package com.gp.nut.schedule.entity;

import com.gp.nut.schedule.dto.GatheringRequestDto;
import com.gp.nut.schedule.dto.GatheringResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_gathering") // 테이블명
@NoArgsConstructor
@AllArgsConstructor // @RequiredArgsConstructor는
@Getter
@Builder
@ToString
public class Gathering {

  @GeneratedValue(strategy = GenerationType.IDENTITY) // pk는 identity옵션이다.
  @Id
  private Long id; // pk

  private String gatheringName; // 회식명

  @Column(nullable = false)
  private Long bossId; // 회식 만든 사장의 id

  @Column(nullable = false)
  private LocalDate date; // 회식 날짜

  private Long confirmedLocationId; // 확정 회식 장소

  @NotEmpty // 컬렉션이 빈값이 아닌지 체크
  @ElementCollection // 기본값이나 임베디드 타입의 컬렉션을 별도 테이블에 매핑
  private List<Long> participantIds; // 참여 사원들

  @NotEmpty // 컬렉션이 빈값이 아닌지 체크
  @ElementCollection
  private  List<Long> candidateLocationIds; // 회식 장소 후보 목록들

  @ElementCollection
  private List<Long> reviewIds;

  // 날짜 변경 메서드
  public void updateDate(LocalDate date) {
    this.date = date;
  }

  // 최종 회식 장소 변경 메서드
  public void updateConfirmedLocationId(Long confirmedLocationId) {
    this.confirmedLocationId = confirmedLocationId;
  }

  // 리뷰 추가 메서드
  public void addReviewId(Long reviewId) {
    this.reviewIds.add(reviewId);
  }

  // Gathering을 Request로 변환
  public GatheringRequestDto toRequestDto() {
    return GatheringRequestDto.builder()
        .gatheringName(gatheringName)
        .bossId(bossId)
        .Date(date)
        .confirmedLocationId(confirmedLocationId)
        .participantIds(participantIds)
        .candidateLocationIds(candidateLocationIds)
        .reviewIds(reviewIds)
        .build();
  }

  // Gathering을 Response로 변환
  public GatheringResponseDto toResponseDto() {
    return GatheringResponseDto.builder()
        .id(id)
        .gatheringName(gatheringName)
        .bossId(bossId)
        .Date(date)
        .confirmedLocationId(confirmedLocationId)
        .participantIds(participantIds)
        .candidateLocationIds(candidateLocationIds)
        .reviewIds(reviewIds)
        .build();
  }

}
