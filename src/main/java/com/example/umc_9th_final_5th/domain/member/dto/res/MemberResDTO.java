package com.example.umc_9th_final_5th.domain.member.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record JoinResultDTO(
            Long memberId,
            LocalDateTime createdAt
    ){}

    @Builder
    public record JoinDTO(
            Long memberId,
            LocalDateTime createdAt
    ){}
}
