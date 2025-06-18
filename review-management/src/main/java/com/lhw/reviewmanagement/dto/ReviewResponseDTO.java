package com.lhw.reviewmanagement.dto;

import com.lhw.reviewmanagement.Entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "리뷰 응답 DTO")
public class ReviewResponseDTO{
    @Schema(description = "리뷰 ID", example = "101")
    private Long reviewId;

    @Schema(description = "스케줄 ID", example = "202")
    private Long scheduleId;

    @Schema(description = "리뷰 내용", example = "분위기 좋고 음식이 맛있어요.")
    private String comment;

    public ReviewResponseDTO(Review review){
        this.reviewId = review.getReviewId();
        this.scheduleId = review.getScheduleId();
        this.comment = review.getComment();
    }

}
