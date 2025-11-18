package com.example.umc_9th_final_5th.domain.store.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class StoreResDTO {

    /**
     * 가게 생성 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateStoreResultDTO {
        private Long storeId;
        private String name;
        private Long managerNumber;
        private String detailAddress;
        private Long locationId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
