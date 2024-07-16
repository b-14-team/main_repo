package com.wolf.workflow;

import com.wolf.workflow.board.entity.*;
import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.user.entity.User;
import com.wolf.workflow.user.entity.UserRole;
import com.wolf.workflow.user.entity.UserStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDB {

    private final InitService initService;


    @PostConstruct
    public void init() {
//        initService.dbInit1();
//        initService.dbInit3();
//        initService.dbInit4();
//        initService.dbInit5();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Autowired
        PasswordEncoder passwordEncoder;

        public void dbInit1() {
            /**
             * 회원
             */
            User consumer = User.builder()
                    .email("b14user@gmail.com")
                    .password(passwordEncoder.encode("Passw0rd!"))
                    .nickName("테스트용 닉네임")
                    .description("한줄 소개")
                    .userStatus(UserStatus.ENABLE)
                    .userRole(UserRole.USER)
                    .build();
            save(consumer);

            User consumer2 = User.builder()
                    .email("test@gmail.com")
                    .password(passwordEncoder.encode("test1234!@"))
                    .nickName("테스트유저")
                    .description("B14조 입니다.")
                    .userStatus(UserStatus.ENABLE)
                    .userRole(UserRole.ADMIN)
                    .build();
            save(consumer2);

            Board board = Board.builder()
                    .board_name("테스트용 보드")
                    .content("테스트용 보드입니다")
                    .build();
            save(board);

            BoardUser boardUser = BoardUser.builder()
                    .board(board)
                    .user(consumer)
                    .boardUserRole(BoardUserRole.GENERAL_MANAGER)
                    .invitationStatus(InvitationStatus.ACCEPTED)
                    .participation(Participation.ENABLE)
                    .build();
            save(boardUser);

            BoardUser boardUser2 = BoardUser.builder()
                    .board(board)
                    .user(consumer2)
                    .boardUserRole(BoardUserRole.GENERAL_MANAGER)
                    .invitationStatus(InvitationStatus.ACCEPTED)
                    .participation(Participation.ENABLE)
                    .build();
            save(boardUser2);


        }


        public void dbInit3() {
            Columns columns = Columns.builder()
                    .columnsStatus("시작전")
                    .color("green")
                    .build();
            save(columns);


        }

        public void dbInit4() {
            Columns columns = Columns.builder()
                    .columnsStatus("진행중")
                    .color("blue")
                    .build();
            save(columns);
        }

        public void dbInit5() {
            Columns columns = Columns.builder()
                    .columnsStatus("완료")
                    .color("purple")
                    .build();
            save(columns);
        }

        public void save(Object... objects) {
            for (Object object : objects) {
                em.persist(object);
            }
        }
    }
}