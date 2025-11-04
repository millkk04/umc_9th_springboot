package com.example.umc_9th_final_5th.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="review_photo")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewPhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_photo_id")
    private Long id;

    @Column(name="photo_url", length=255)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_review_photo__review"))
    private Review review;
}
