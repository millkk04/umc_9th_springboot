package com.example.umc_9th_final_5th.domain.mission.service.command;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.member.repository.MemberMissionRepository;
import com.example.umc_9th_final_5th.domain.member.repository.MemberRepository;
import com.example.umc_9th_final_5th.domain.mission.converter.MissionConverter;
import com.example.umc_9th_final_5th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import com.example.umc_9th_final_5th.domain.mission.exception.MissionException;
import com.example.umc_9th_final_5th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc_9th_final_5th.domain.mission.repository.MissionRepository;
import com.example.umc_9th_final_5th.domain.store.entity.Store;
import com.example.umc_9th_final_5th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandServiceImpl implements MissionCommandService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final StoreRepository storeRepository;

    @Override
    public MemberMission challengeMission(Long memberId, Long missionId) {
        // 1. 미션 존재 여부 확인
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        // 2. 회원 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MEMBER_NOT_FOUND));

        // 3. 미션 마감일 확인
        if (mission.getDeadline().isBefore(LocalDate.now())) {
            throw new MissionException(MissionErrorCode.MISSION_EXPIRED);
        }

        // 4. 중복 도전 확인
        if (memberMissionRepository.existsByMemberIdAndMissionId(memberId, missionId)) {
            throw new MissionException(MissionErrorCode.MISSION_ALREADY_CHALLENGING);
        }

        // 5. MemberMission 엔티티 생성 및 저장
        MemberMission memberMission = MissionConverter.toMemberMission(member, mission);
        return memberMissionRepository.save(memberMission);
    }

    @Override
    public Mission createMission(Long storeId, MissionReqDTO.CreateMissionDTO request) {
        // 1. Store 존재 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.STORE_NOT_FOUND));

        // 2. Mission 엔티티 생성
        Mission mission = MissionConverter.toMission(request, store);

        // 3. 저장 및 반환
        return missionRepository.save(mission);
    }
}
