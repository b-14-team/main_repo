//package com.wolf.workflow;
//
//import com.wolf.workflow.user.entity.User;
//import com.wolf.workflow.user.entity.UserStatus;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class TestDB {
//
//    private final InitService initService;
//
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
////        @Autowired
////        PasswordEncoder passwordEncoder;
//
//        public void dbInit1() {
//            /**
//             * 회원
//             */
//            User consumer = User.builder()
//                    .email("b14user@gmail.com")
//                    .password("Passw0rd!")
//                    .nickName("닉네임이요~~")
//                    .description("한줄 소개")
//                    .userStatus(UserStatus.ENABLE)
//                    .build();
//            save(consumer);
//        }
//
//
//        public void save(Object... objects) {
//            for (Object object : objects) {
//                em.persist(object);
//            }
//        }
//    }
//}
//
