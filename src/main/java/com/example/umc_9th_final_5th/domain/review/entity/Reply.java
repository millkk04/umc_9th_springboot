package com.example.umc_9th_final_5th.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="reply")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reply_id")
    private Long id;

    @Lob
    @Column(name="content", nullable=false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_reply__review"))
    private Review review;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
