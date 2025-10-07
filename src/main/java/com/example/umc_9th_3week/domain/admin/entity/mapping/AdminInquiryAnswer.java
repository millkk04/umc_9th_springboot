package com.example.umc_9th_3week.domain.admin.entity.mapping;

import com.example.umc_9th_3week.domain.admin.entity.Admin;
import com.example.umc_9th_3week.domain.inquiry.entity.InquiryAnswer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inquiry_answers")
@Getter
@NoArgsConstructor
public class AdminInquiryAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: admin_id → admins.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    /** FK: inquiry_id → inquiries.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private com.example.umc_9th_3week.domain.inquiry.entity.Inquiry inquiry;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;
}
