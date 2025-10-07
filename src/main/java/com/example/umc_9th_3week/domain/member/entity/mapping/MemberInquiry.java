package com.example.umc_9th_3week.domain.member.entity.mapping;

import com.example.umc_9th_3week.domain.inquiry.entity.Inquiry;
import com.example.umc_9th_3week.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inquiries")
@Getter
@NoArgsConstructor
public class MemberInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: user_id → users.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.example.umc_9th_3week.domain.inquiry.enums.InquiryCategory category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.example.umc_9th_3week.domain.inquiry.enums.InquiryStatus status
            = com.example.umc_9th_3week.domain.inquiry.enums.InquiryStatus.대기중;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.example.umc_9th_3week.domain.inquiry.entity.InquiryAnswer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.example.umc_9th_3week.domain.inquiry.entity.InquiryImage> images = new ArrayList<>();
}
