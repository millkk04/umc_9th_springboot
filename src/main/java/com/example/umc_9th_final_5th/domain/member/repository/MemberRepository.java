package com.example.umc_9th_final_5th.domain.member.repository;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //현재 포인트
    @Query("select m.point from Member m where m.id = :memberId")
    Integer findPoint(@Param("memberId") Long memberId);

    //완료 미션 수
    @Query("select count(mm) from MemberMission mm where mm.member.id = :memberId and mm.isComplete = true")
    Long countCompleted(@Param("memberId") Long memberId);

    //진행중 미션 수
    @Query("select count(mm) from MemberMission mm where mm.member.id = :memberId and mm.isComplete = false")
    Long countInProgress(@Param("memberId") Long memberId);
}
