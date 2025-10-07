package com.example.umc_9th_3week.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agreements")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(nullable = false)
    private Boolean over14;

    @Column(nullable = false)
    private Boolean termsOfService;

    @Column(nullable = false)
    private Boolean privacyPolicy;

    private Boolean locationService;
    private Boolean marketing;
}
