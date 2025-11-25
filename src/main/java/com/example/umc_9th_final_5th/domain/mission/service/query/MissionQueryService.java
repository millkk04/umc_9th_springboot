package com.example.umc_9th_final_5th.domain.mission.service.query;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

public interface MissionQueryService {

    /**
     * 특정 가게의 미션 목록 조회 (페이징)
     * @param storeId 가게 ID
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 페이징된 미션 목록
     */
    Page<Mission> getMissionsByStore(Long storeId, Integer page, Integer size);

    /**
     * 회원이 진행 중인 미션 목록 조회 (페이징)
     * @param memberId 회원 ID
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 페이징된 MemberMission 목록
     */
    Page<MemberMission> getMyMissions(Long memberId, Integer page, Integer size);
}
