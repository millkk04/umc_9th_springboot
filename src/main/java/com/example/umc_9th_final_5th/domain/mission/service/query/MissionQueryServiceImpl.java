package com.example.umc_9th_final_5th.domain.mission.service.query;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.member.exception.MemberException;
import com.example.umc_9th_final_5th.domain.member.exception.code.MemberErrorCode;
import com.example.umc_9th_final_5th.domain.member.repository.MemberMissionRepository;
import com.example.umc_9th_final_5th.domain.member.repository.MemberRepository;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import com.example.umc_9th_final_5th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc_9th_final_5th.domain.mission.repository.MissionRepository;
import com.example.umc_9th_final_5th.domain.store.exception.StoreException;
import com.example.umc_9th_final_5th.domain.store.exception.code.StoreErrorCode;
import com.example.umc_9th_final_5th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public Page<Mission> getMissionsByStore(Long storeId, Integer page, Integer size) {
        // 1. 가게 존재 여부 확인
        if (!storeRepository.existsById(storeId)) {
            throw new StoreException(StoreErrorCode.STORE_NOT_FOUND);
        }

        // 2. 페이징 정보 생성 (최신순 정렬)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 3. 미션 목록 조회 및 반환
        return missionRepository.findByStoreId(storeId, pageable);
    }

    @Override
    public Page<MemberMission> getMyMissions(Long memberId, Integer page, Integer size) {
        // 1. 회원 존재 여부 확인
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MissionErrorCode.MEMBER_NOT_FOUND);
        }

        // 2. 페이징 정보 생성 (최신순 정렬)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 3. 회원이 진행 중인 미션 목록 조회 및 반환
        return memberMissionRepository.findByMemberId(memberId, pageable);
    }
}
