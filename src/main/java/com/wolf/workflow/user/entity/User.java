package com.wolf.workflow.user.entity;

import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.comment.entity.Comment;
import com.wolf.workflow.common.Timestamped;
import com.wolf.workflow.user.dto.request.UserSignupRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String nickName;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BoardUser> boardUserList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Builder
    public User(String email, String password, String nickName, String description,
                String refreshToken, List<Comment> commentList, List<BoardUser> boardUserList,
                UserRole userRole, UserStatus userStatus) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.description = description;
        this.refreshToken = refreshToken;
        this.commentList = commentList;
        this.boardUserList = boardUserList;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public static User createUser(UserSignupRequestDto requestDto) {
        return User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickName(requestDto.getNickName())
                .description(requestDto.getDescription())
                .userStatus(UserStatus.ENABLE)
                .build();
    }

    public void updateStatus() {
        this.userStatus = UserStatus.DISABLE;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}