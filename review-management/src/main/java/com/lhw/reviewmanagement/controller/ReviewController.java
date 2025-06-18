package com.lhw.reviewmanagement.controller;


import com.lhw.reviewmanagement.Entity.Review;
import com.lhw.reviewmanagement.dto.ReviewDTO;
import com.lhw.reviewmanagement.dto.ReviewResponseDTO;
import com.lhw.reviewmanagement.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name="리뷰 API", description = "리뷰 등록/수정/조회/삭제")
public class ReviewController {
    private  final ReviewService reviewService;

    @GetMapping("/my")
    @Operation(summary="리뷰 조회", description = "리뷰를 조회합니다.")
    public ResponseEntity<List<ReviewResponseDTO>> getMyReview(@RequestHeader("X-User-Id") String userId)
    {
        List<ReviewResponseDTO> reviews = reviewService.getReviewByUserId(userId);
        return ResponseEntity.ok(reviews);
    }


    @PostMapping
    @Operation(summary="리뷰 등록", description = "리뷰를 등록합니다.")
    public ResponseEntity<ReviewResponseDTO> createReview(
            @RequestBody ReviewDTO reviewDTO,
            @RequestHeader("X-User-Id") String userId
    ) {
        return ResponseEntity.ok(reviewService.saveReview(reviewDTO, userId));
    }

    @PutMapping("/{id}")
    @Operation(summary="리뷰 수정", description = "리뷰를 수정합니다.")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewDTO reviewDTO
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }
    @DeleteMapping
    @Operation(summary="리뷰 삭제", description = "리뷰를 삭제합니다.")
    public ResponseEntity<String> deleteReview(
            @RequestHeader ("X-User-Id") String userId,
            @RequestBody DeleteReviewRequest request
    ) {
        reviewService.deleteReview(userId,request.getScheduleId());
        return ResponseEntity.ok("삭제에 성공하였습니다.");
    }
}
