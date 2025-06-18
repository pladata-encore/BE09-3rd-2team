package com.gp.nut.schedule.dto;

import com.gp.nut.schedule.entity.Gathering;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // 모든 필드 받는 생성자 자동으로 만들어줌
public class GatheringResponseDto {

  private Long id; // 어떤 회식인지 알기 위해 필요
  private String gatheringName;
  private Long bossId;
  private LocalDate Date;
  private Long confirmedLocationId;
  private List<Long> participantIds;
  private List<Long> candidateLocationIds;
  private List<Long> reviewIds;

  public Gathering toGathering() {
    return Gathering.builder()
        .id(id)
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
