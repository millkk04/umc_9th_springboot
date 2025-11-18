package com.example.umc_9th_final_5th.domain.member.repository;

import com.example.umc_9th_final_5th.domain.member.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}

