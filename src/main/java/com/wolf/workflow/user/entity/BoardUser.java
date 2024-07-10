package com.wolf.workflow.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Participation participation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardUserRole boardUserRole;

}
