package com.wolf.workflow.board.entity;

import com.wolf.workflow.common.Timestamped;
import com.wolf.workflow.user.entity.BoardUserRole;
import com.wolf.workflow.user.entity.Participation;
import com.wolf.workflow.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_user_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUser extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Participation participation;

    @Column
    private BoardUserRole role;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;



}
