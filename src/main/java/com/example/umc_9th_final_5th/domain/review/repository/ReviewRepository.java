package com.example.umc_9th_final_5th.domain.review.repository;

import com.example.umc_9th_final_5th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByWriter_IdOrderByCreatedAtDesc(Long memberId);

    Page<Review> findByWriterId(Long memberId, Pageable pageable);
}
