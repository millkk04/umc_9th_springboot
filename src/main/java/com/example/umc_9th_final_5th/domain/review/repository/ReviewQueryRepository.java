package com.example.umc_9th_final_5th.domain.review.repository;

import com.example.umc_9th_final_5th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewQueryRepository extends JpaRepository<Review, Long> {

    // 기본: 내가 작성한 모든 리뷰
    List<Review> findByWriter_IdOrderByCreatedAtDesc(Long memberId);

    // 가게 이름으로 필터링
    @Query("SELECT r FROM Review r JOIN r.store s WHERE r.writer.id = :memberId AND s.name LIKE %:storeName% ORDER BY r.createdAt DESC")
    List<Review> findByMemberIdAndStoreNameContaining(@Param("memberId") Long memberId, @Param("storeName") String storeName);

    // 별점으로 필터링 (정확한 별점)
    @Query("SELECT r FROM Review r WHERE r.writer.id = :memberId AND r.star = :star ORDER BY r.createdAt DESC")
    List<Review> findByMemberIdAndStar(@Param("memberId") Long memberId, @Param("star") Float star);

    // 별점 범위로 필터링 (4점대, 3점대 등)
    @Query("SELECT r FROM Review r WHERE r.writer.id = :memberId AND r.star >= :minStar AND r.star < :maxStar ORDER BY r.createdAt DESC")
    List<Review> findByMemberIdAndStarBetween(@Param("memberId") Long memberId, @Param("minStar") Float minStar, @Param("maxStar") Float maxStar);

    // 가게 이름 + 별점으로 필터링
    @Query("SELECT r FROM Review r JOIN r.store s WHERE r.writer.id = :memberId AND s.name LIKE %:storeName% AND r.star = :star ORDER BY r.createdAt DESC")
    List<Review> findByMemberIdAndStoreNameAndStar(@Param("memberId") Long memberId, @Param("storeName") String storeName, @Param("star") Float star);

    // 가게 이름 + 별점 범위로 필터링
    @Query("SELECT r FROM Review r JOIN r.store s WHERE r.writer.id = :memberId AND s.name LIKE %:storeName% AND r.star >= :minStar AND r.star < :maxStar ORDER BY r.createdAt DESC")
    List<Review> findByMemberIdAndStoreNameAndStarBetween(@Param("memberId") Long memberId, @Param("storeName") String storeName, @Param("minStar") Float minStar, @Param("maxStar") Float maxStar);

    // ========== 페이징 처리된 메서드들 ==========

    // 가게 이름으로 필터링 (페이징)
    @Query("SELECT r FROM Review r JOIN r.store s WHERE r.writer.id = :memberId AND s.name LIKE %:storeName%")
    Page<Review> findByMemberIdAndStoreNameContaining(@Param("memberId") Long memberId, @Param("storeName") String storeName, Pageable pageable);

    // 별점으로 필터링 (정확한 별점, 페이징)
    @Query("SELECT r FROM Review r WHERE r.writer.id = :memberId AND r.star = :star")
    Page<Review> findByMemberIdAndStar(@Param("memberId") Long memberId, @Param("star") Float star, Pageable pageable);

    // 별점 범위로 필터링 (페이징)
    @Query("SELECT r FROM Review r WHERE r.writer.id = :memberId AND r.star >= :minStar AND r.star < :maxStar")
    Page<Review> findByMemberIdAndStarBetween(@Param("memberId") Long memberId, @Param("minStar") Float minStar, @Param("maxStar") Float maxStar, Pageable pageable);

    // 가게 이름 + 별점으로 필터링 (페이징)
    @Query("SELECT r FROM Review r JOIN r.store s WHERE r.writer.id = :memberId AND s.name LIKE %:storeName% AND r.star = :star")
    Page<Review> findByMemberIdAndStoreNameAndStar(@Param("memberId") Long memberId, @Param("storeName") String storeName, @Param("star") Float star, Pageable pageable);

    // 가게 이름 + 별점 범위로 필터링 (페이징)
    @Query("SELECT r FROM Review r JOIN r.store s WHERE r.writer.id = :memberId AND s.name LIKE %:storeName% AND r.star >= :minStar AND r.star < :maxStar")
    Page<Review> findByMemberIdAndStoreNameAndStarBetween(@Param("memberId") Long memberId, @Param("storeName") String storeName, @Param("minStar") Float minStar, @Param("maxStar") Float maxStar, Pageable pageable);
}
