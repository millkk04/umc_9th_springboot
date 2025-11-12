package com.example.umc_9th_final_5th.domain.mission.repository;

import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    //홈 화면 – 선택 지역에서 도전 가능한 미션 목록 (페이징)
    @Query("""
        select m
        from Mission m
          join m.store s
          join s.location l
        where l.id = :locationId
          and m.id not in (
              select mm.mission.id
              from MemberMission mm
              where mm.member.id = :memberId
          )
        order by m.createdAt desc
        """)
    Page<Mission> findAvailableInLocation(
            @Param("locationId") Long locationId,
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
