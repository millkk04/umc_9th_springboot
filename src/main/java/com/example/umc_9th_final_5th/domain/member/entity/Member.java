package com.example.umc_9th_final_5th.domain.member.entity;

import com.example.umc_9th_final_5th.domain.member.enums.Gender;
import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberFood;
import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberTerm;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.store.enums.Address;
import com.example.umc_9th_final_5th.global.auth.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name="name", nullable=false, length=50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="gender", nullable=false)
    private Gender gender;

    @Column(name="birth", nullable=false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name="address", nullable=false)
    private Address address;

    @Column(name="detail_address", nullable=false, length=255)
    private String detailAddress;

    @Column(name="social_uid", nullable=false, length=100)
    private String socialUid;

    @Enumerated(EnumType.STRING)
    @Column(name="social_type", nullable=false)
    private SocialType socialType;

    @Column(name="point", nullable=false)
    private Integer point;

    @Column(name="email", nullable=false, unique=true, length=100)
    private String email;

    @Column(name="phone_number", length=20)
    private String phoneNumber;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    // --- Relations ---
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<MemberFood> memberFoods = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<MemberTerm> memberTerms = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<MemberMission> memberMissions = new ArrayList<>();

    @OneToMany(mappedBy = "writer", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    // defaults aligned with DB
    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if (point == null) point = 0;
        if (gender == null) gender = Gender.NONE;
        if (address == null) address = Address.NONE;
    }

    @PreUpdate
    void preUpdate() { updatedAt = LocalDateTime.now(); }
}
