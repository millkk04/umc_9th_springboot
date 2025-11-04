package com.example.umc_9th_final_5th.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private Long id;

    @Column(name="name", nullable=false, unique=true, length=100)
    private String name;
}
