package com.example.umc_9th_final_5th.domain.mission.converter;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc_9th_final_5th.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import com.example.umc_9th_final_5th.domain.store.entity.Store;

public class MissionConverter {

    /**
     * Member + Mission -> MemberMission 엔티티 생성
     */
    public static MemberMission toMemberMission(Member member, Mission mission) {
        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .isComplete(false)  // 초기값: 미완료
                .build();
    }

    /**
     * MemberMission 엔티티 -> ChallengeMissionResultDTO 변환
     */
    public static MissionResDTO.ChallengeMissionResultDTO toChallengeMissionResultDTO(MemberMission memberMission) {
        Mission mission = memberMission.getMission();

        return MissionResDTO.ChallengeMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .memberId(memberMission.getMember().getId())
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .isComplete(memberMission.getIsComplete())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }

    /**
     * CreateMissionDTO + Store -> Mission 엔티티 생성
     */
    public static Mission toMission(MissionReqDTO.CreateMissionDTO request, Store store) {
        return Mission.builder()
                .deadline(request.deadline())
                .conditional(request.conditional())
                .point(request.point())
                .store(store)
                .build();
    }

    /**
     * Mission 엔티티 -> CreateMissionResultDTO 변환
     */
    public static MissionResDTO.CreateMissionResultDTO toCreateMissionResultDTO(Mission mission) {
        return MissionResDTO.CreateMissionResultDTO.builder()
                .missionId(mission.getId())
                .deadline(mission.getDeadline())
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .createdAt(mission.getCreatedAt())
                .storeId(mission.getStore().getId())
                .build();
    }
}
