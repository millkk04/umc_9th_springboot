package com.example.umc_9th_final_5th.domain.member.converter;

import com.example.umc_9th_final_5th.domain.member.dto.req.MemberReqDTO;
import com.example.umc_9th_final_5th.domain.member.dto.res.MemberResDTO;
import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.global.auth.enums.SocialType;

public class MemberConverter {

    // Entity -> DTO
    public static MemberResDTO.JoinDTO toJoinDTO(
            Member member
    ){
        return MemberResDTO.JoinDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    // DTO -> Entity
    public static Member toMember(
            MemberReqDTO.JoinDTO dto
    ){
        return Member.builder()
                .name(dto.name())
                .birth(dto.birth())
                .address(dto.address())
                .detailAddress(dto.specAddress())
                .gender(dto.gender())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .socialUid("temp_uid_" + System.currentTimeMillis())
                .socialType(SocialType.KAKAO)
                .point(0)
                .build();
    }
}
