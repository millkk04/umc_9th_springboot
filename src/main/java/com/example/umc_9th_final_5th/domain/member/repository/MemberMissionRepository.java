package com.example.umc_9th_final_5th.domain.member.repository;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    /**
     * 특정 회원의 진행 중인 미션 목록 조회 (페이징)
     * @param memberId 회원 ID
     * @param pageable 페이징 정보
     * @return 페이징된 MemberMission 목록
     */
    Page<MemberMission> findByMemberId(Long memberId, Pageable pageable);

    /**
     * 특정 회원이 특정 미션을 이미 도전 중인지 확인
     * @param memberId 회원 ID
     * @param missionId 미션 ID
     * @return 이미 도전 중이면 true
     */
    boolean existsByMemberIdAndMissionId(Long memberId, Long missionId);

    /**
     * 진행 중인 미션 목록 조회 (페이징, Join Fetch 최적화)
     * @param memberId 회원 ID
     * @param pageable 페이징 정보
     * @return 페이징된 진행 중인 MemberMission 목록
     */
    @Query("""
           select mm
           from MemberMission mm
             join fetch mm.mission m
             join fetch m.store s
           where mm.member.id = :memberId
             and mm.isComplete = false
           """)
    Page<MemberMission> findInProgress(@Param("memberId") Long memberId, Pageable pageable);

    /**
     * 완료된 미션 목록 조회 (페이징, Join Fetch 최적화)
     * @param memberId 회원 ID
     * @param pageable 페이징 정보
     * @return 페이징된 완료된 MemberMission 목록
     */
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

