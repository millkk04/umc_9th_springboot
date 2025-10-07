package com.example.umc_9th_3week.domain.member.entity.mapping;

import com.example.umc_9th_3week.domain.member.entity.Agreement;
import com.example.umc_9th_3week.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agreements")
@Getter
@NoArgsConstructor
public class MemberAgreement {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(name = "over_14", nullable = false)
    private Boolean over14 = false;

    @Column(name = "terms_of_service", nullable = false)
    private Boolean termsOfService = false;

    @Column(name = "privacy_policy", nullable = false)
    private Boolean privacyPolicy = false;

    private Boolean locationService = false;
    private Boolean marketing = false;
}
