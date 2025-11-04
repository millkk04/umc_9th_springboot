package com.example.umc_9th_final_5th.domain.mission.repository;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    //진행중 미션 (페이징)
    @Query("""
           select mm
           from MemberMission mm
             join fetch mm.mission m
             join fetch m.store s
           where mm.member.id = :memberId
             and mm.isComplete = false
           """)
    Page<MemberMission> findInProgress(@Param("memberId") Long memberId, Pageable pageable);

    //완료 미션 (페이징)
    @Query("""
           select mm
           from MemberMission mm
             join fetch mm.mission m
             join fetch m.store s
           where mm.member.id = :memberId
             and mm.isComplete = true
           """)
    Page<MemberMission> findCompleted(@Param("memberId") Long memberId, Pageable pageable);
}
