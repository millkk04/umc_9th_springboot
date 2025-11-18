package com.example.umc_9th_final_5th.domain.store.service.command;

import com.example.umc_9th_final_5th.domain.store.dto.req.StoreReqDTO;
import com.example.umc_9th_final_5th.domain.store.entity.Store;

public interface StoreCommandService {

    /**
     * 가게 생성
     * @param request 가게 생성 요청 DTO
     * @return 생성된 가게 엔티티
     */
    Store createStore(StoreReqDTO.CreateStoreDTO request);
}
