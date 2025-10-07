package com.example.umc_9th_3week.domain.inquiry.entity.mapping;

import com.example.umc_9th_3week.domain.admin.entity.Admin;
import com.example.umc_9th_3week.domain.inquiry.entity.InquiryAnswer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inquiry_answers")
@Getter
@NoArgsConstructor
public class InquiryAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: admin_id → admins.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<InquiryAnswer> answers = new ArrayList<>();
}
