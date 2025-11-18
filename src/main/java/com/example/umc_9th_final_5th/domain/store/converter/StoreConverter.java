package com.example.umc_9th_final_5th.domain.store.converter;

import com.example.umc_9th_final_5th.domain.store.dto.req.StoreReqDTO;
import com.example.umc_9th_final_5th.domain.store.dto.res.StoreResDTO;
import com.example.umc_9th_final_5th.domain.store.entity.Location;
import com.example.umc_9th_final_5th.domain.store.entity.Store;

public class StoreConverter {

    /**
     * CreateStoreDTO + Location -> Store 엔티티 변환
     */
    public static Store toStore(StoreReqDTO.CreateStoreDTO request, Location location) {
        return Store.builder()
                .name(request.name())
                .managerNumber(request.managerNumber())
                .detailAddress(request.detailAddress())
                .location(location)
                .build();
    }

    /**
     * Store 엔티티 -> CreateStoreResultDTO 변환
     */
    public static StoreResDTO.CreateStoreResultDTO toCreateStoreResultDTO(Store store) {
        return StoreResDTO.CreateStoreResultDTO.builder()
                .storeId(store.getId())
                .name(store.getName())
                .managerNumber(store.getManagerNumber())
                .detailAddress(store.getDetailAddress())
                .locationId(store.getLocation().getId())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }
}
