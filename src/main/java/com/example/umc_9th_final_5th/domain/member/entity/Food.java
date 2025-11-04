package com.example.umc_9th_final_5th.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private FoodName name;

    public enum FoodName {
        NONE, KOREAN, JAPANESE, CHINESE, WESTERN, SNACK, CAFE, DESSERT
    }
}
