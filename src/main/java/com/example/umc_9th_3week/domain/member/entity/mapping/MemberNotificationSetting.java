package com.example.umc_9th_3week.domain.member.entity.mapping;

import com.example.umc_9th_3week.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_settings")
@Getter
@NoArgsConstructor
public class MemberNotificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: user_id → users.id */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @Column(name = "event_notifications", nullable = false)
    private Boolean eventNotifications = true;

    @Column(name = "review_reply_notifications", nullable = false)
    private Boolean reviewReplyNotifications = true;

    @Column(name = "inquiry_reply_notifications", nullable = false)
    private Boolean inquiryReplyNotifications = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
