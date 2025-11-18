package com.example.umc_9th_final_5th.domain.mission.service.command;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;

public interface MissionCommandService {

    /**
     * 미션 도전하기
     * @param memberId 회원 ID
     * @param missionId 미션 ID
     * @return 생성된 MemberMission 엔티티
     */
    MemberMission challengeMission(Long memberId, Long missionId);

    /**
     * 미션 생성
     * @param storeId 가게 ID
     * @param request 미션 생성 요청 DTO
     * @return 생성된 Mission 엔티티
     */
    Mission createMission(Long storeId, MissionReqDTO.CreateMissionDTO request);
}
