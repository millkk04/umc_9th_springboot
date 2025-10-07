package com.example.umc_9th_3week.domain.inquiry.entity.mapping;

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
public class InquiryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: user_id → users.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private List<Inquiry> inquiries = new ArrayList<>();
}
