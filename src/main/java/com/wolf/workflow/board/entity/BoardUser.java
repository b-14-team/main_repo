package com.wolf.workflow.board.entity;

import com.wolf.workflow.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Participation participation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardUserRole boardUserRole;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    @Builder
    private BoardUser(Board board, User user, Participation participation,
        BoardUserRole boardUserRole, InvitationStatus invitationStatus) {
        this.board = board;
        this.user = user;
        this.participation = participation;
        this.boardUserRole = boardUserRole;
        this.invitationStatus = invitationStatus;
    }
    public static BoardUser createBoardUser(Board board, User user, Participation participation,
        BoardUserRole boardUserRole, InvitationStatus invitationStatus) {
        return BoardUser.builder()
            .board(board)
            .user(user)
            .participation(participation)
            .boardUserRole(boardUserRole)
            .invitationStatus(invitationStatus)
            .build();
    }

    public void updateInvitationStatus(boolean approve) {
        this.invitationStatus = approve ? InvitationStatus.ACCEPTED : InvitationStatus.DECLINED;
    }

}

