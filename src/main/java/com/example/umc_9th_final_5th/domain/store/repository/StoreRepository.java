package com.example.umc_9th_final_5th.domain.store.repository;

import com.example.umc_9th_final_5th.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}

