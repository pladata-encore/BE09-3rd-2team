package com.lhw.reviewmanagement.repository;

import com.lhw.reviewmanagement.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
@RequestMapping("/reviews")
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewId(Long reviewId);

    Optional<Review> findByScheduleIdAndUserId(Long scheduleId, String userId);

    List<Review> findByUserId(String userId);

    boolean existsByScheduleIdAndUserId(Long scheduleId, String userId);
}
