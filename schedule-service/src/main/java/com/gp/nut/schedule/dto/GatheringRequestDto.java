package com.gp.nut.schedule.dto;

import com.gp.nut.schedule.entity.Gathering;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder // 스태틱임. 모든 필드를 받는 생성자를 호출하여 객체를 만드므로 @AllArgsConstructor가 필수이다.
public class GatheringRequestDto {
  private Long id;

  private String gatheringName;

  @NotNull(message = "회식을 주최하는 사장 ID는  필수입니다.")
  private Long bossId;

  @NotNull(message = "회식 날짜는 필수입니다.")
  private LocalDate Date;

  @NotEmpty(message = "참여자 ID 목록은 비어있을 수 없습니다.") // null과 빈 리스트를 실패 처리
  private List<Long> participantIds;

  @NotEmpty(message = "회식 장소 후보 ID 목록은 비어있을 수 없습니다.") // null과 빈 리스트를 실패 처리
  private List<Long> candidateLocationIds;

  private Long confirmedLocationId; // 확정 장소는 없어도 회식 생성 가능
  private List<Long> reviewIds;

  public Gathering toGathering() {
    return Gathering.builder()
        .gatheringName(gatheringName)
        .bossId(bossId)
        .date(Date)
        .confirmedLocationId(confirmedLocationId)
        .participantIds(participantIds)
        .candidateLocationIds(candidateLocationIds)
        .reviewIds(reviewIds)
        .build();
  }

}
