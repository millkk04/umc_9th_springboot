package com.example.umc_9th_3week.domain.member.entity;

import com.example.umc_9th_3week.domain.member.enums.Gender;
import com.example.umc_9th_3week.domain.member.entity.mapping.*;
import com.example.umc_9th_3week.domain.notification.entity.mapping.*;
import com.example.umc_9th_3week.domain.review.entity.Review;
import com.example.umc_9th_3week.domain.review.entity.mapping.*;
import com.example.umc_9th_3week.domain.inquiry.entity.mapping.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Gender gender = Gender.선택안함;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(length = 255)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 약관 동의 (1:1)
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberAgreement agreement;

    // 선호 음식 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPreference> preferences = new ArrayList<>();

    // 위치 정보 (1:1)
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberLocation location;

    // 미션 도전 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMission> userMissions = new ArrayList<>();

    // 포인트 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPoint> userPoints = new ArrayList<>();

    // 미션 통계 (1:1)
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberMissionStat missionStat;

    // 리뷰 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // 알림 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationMember> notifications = new ArrayList<>();

    // 알림 설정 (1:1)
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private NotificationSettingMember notificationSetting;

    // 문의 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquiryMember> inquiries = new ArrayList<>();


}
