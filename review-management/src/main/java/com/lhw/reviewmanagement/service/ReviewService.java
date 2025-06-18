package com.lhw.reviewmanagement.service;

import com.lhw.reviewmanagement.Entity.Review;
import com.lhw.reviewmanagement.dto.ReviewDTO;
import com.lhw.reviewmanagement.dto.ReviewResponseDTO;
import com.lhw.reviewmanagement.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public ReviewResponseDTO saveReview(ReviewDTO reviewDTO,String userId) {
        Optional<Review> existing = reviewRepository.findByScheduleIdAndUserId(reviewDTO.getScheduleId(), userId);
        if (reviewRepository.existsByScheduleIdAndUserId(reviewDTO.getScheduleId(), userId)) {
            throw new IllegalArgumentException("이미 작성한 리뷰입니다.");
        }

        Review review = Review.builder()
                .scheduleId(reviewDTO.getScheduleId())
                .comment(reviewDTO.getComment())
                .userId(userId)
                .build();
        Review saved = reviewRepository.save(review);
        return new ReviewResponseDTO(saved);
    }
    @Transactional
    public ReviewResponseDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰가 존재하지 않습니다."));

        review.setComment(reviewDTO.getComment());
        return new ReviewResponseDTO(review);
    }

    @Transactional
    public ReviewResponseDTO deleteReview(String userId,Long scheduleId) {
        Review review = reviewRepository.findByScheduleIdAndUserId(scheduleId,userId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰가 존재하지 않습니다."));
        reviewRepository.delete(review);
        return new ReviewResponseDTO(review);
    }

    public List<ReviewResponseDTO> getReviewByUserId(String userId) {
        List<Review> reviewList = reviewRepository.findByUserId(userId);

        if (reviewList.isEmpty()) {
            throw new NoSuchElementException("해당 리뷰가 존재하지 않습니다.");
        }

        return reviewList.stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }

}
