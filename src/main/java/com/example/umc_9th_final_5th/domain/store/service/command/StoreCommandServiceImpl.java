package com.example.umc_9th_final_5th.domain.store.service.command;

import com.example.umc_9th_final_5th.domain.store.converter.StoreConverter;
import com.example.umc_9th_final_5th.domain.store.dto.req.StoreReqDTO;
import com.example.umc_9th_final_5th.domain.store.entity.Location;
import com.example.umc_9th_final_5th.domain.store.entity.Store;
import com.example.umc_9th_final_5th.domain.store.exception.StoreException;
import com.example.umc_9th_final_5th.domain.store.exception.code.StoreErrorCode;
import com.example.umc_9th_final_5th.domain.store.repository.LocationRepository;
import com.example.umc_9th_final_5th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;

    @Override
    public Store createStore(StoreReqDTO.CreateStoreDTO request) {
        // 1. Location 존재 여부 확인
        Location location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new StoreException(StoreErrorCode.LOCATION_NOT_FOUND));

        // 2. Store 엔티티 생성
        Store store = StoreConverter.toStore(request, location);

        // 3. 저장 및 반환
        return storeRepository.save(store);
    }
}
