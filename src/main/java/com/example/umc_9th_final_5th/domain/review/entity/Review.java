package com.example.umc_9th_final_5th.domain.review.entity;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="review")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @Lob
    @Column(name="content", nullable=false)
    private String content;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="star", nullable=false)
    private Float star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_review__store"))
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_review__member"))
    private Member writer;

    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ReviewPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
