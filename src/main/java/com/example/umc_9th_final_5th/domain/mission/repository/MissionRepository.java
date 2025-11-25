package com.example.umc_9th_final_5th.domain.mission.repository;

import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    /**
     * 특정 가게의 미션 목록 조회 (페이징)
     * @param storeId 가게 ID
     * @param pageable 페이징 정보
     * @return 페이징된 미션 목록
     */
    Page<Mission> findByStoreId(Long storeId, Pageable pageable);
}
