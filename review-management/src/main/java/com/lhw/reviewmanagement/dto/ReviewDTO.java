package com.lhw.reviewmanagement.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "리뷰 요청 DTO")
public class ReviewDTO {


    @Schema(description = "스케줄 ID", example = "123")
    private Long scheduleId;

    @Schema(description = "사용자 ID", example = "user01")
    private String userId;

    @Schema(description = "리뷰 내용", example = "정말 맛있고 친절했어요!")
    private String comment;
}
