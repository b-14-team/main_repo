package com.wolf.workflow.user.adapter;

import com.wolf.workflow.common.exception.DuplicatedEmailException;
import com.wolf.workflow.common.exception.NotFoundUserException;
import com.wolf.workflow.common.util.MessageUtil;
import com.wolf.workflow.user.entity.User;
import com.wolf.workflow.user.entity.UserStatus;
import com.wolf.workflow.user.repository.UserRepository;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;

    /**
     * 이메일 중복 체크
     *
     * @param email 체크할 이메일 주소
     * @throws DuplicatedEmailException 이메일이 이미 존재하는 경우 발생하는 예외
     */
    public void checkDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(
                    MessageUtil.getMessage("duplicated.user.email"));
        }
    }

    /**
     * 주어진 사용자 ID로 사용자 조회
     *
     * @param userId 검색할 사용자 ID. 필수 값입니다.
     * @return 주어진 ID에 해당하는 사용자
     * @throws NotFoundUserException 사용자를 찾을 수 없는 경우 발생하는 예외
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(
                        MessageUtil.getMessage("not.found.user")));
    }

    public User getUserByEmailAndStatus(String email) {
        User user = getUserByEmail(email);
        UserStatus.checkUserStatus(user.getUserStatus());
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException(
                        MessageUtil.getMessage("not.found.user")));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
