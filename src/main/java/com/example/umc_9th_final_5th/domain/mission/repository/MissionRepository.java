package com.example.umc_9th_final_5th.domain.mission.repository;

import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
