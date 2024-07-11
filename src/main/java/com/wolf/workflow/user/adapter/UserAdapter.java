package com.wolf.workflow.user.adapter;

import com.wolf.workflow.common.exception.EmailDuplicatedException;
import com.wolf.workflow.common.exception.UserNotFoundException;
import com.wolf.workflow.user.entity.User;
import com.wolf.workflow.user.repository.UserRepository;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    /**
     * 이메일 중복을 체크합니다.
     * <p>
     * 이메일이 이미 존재하면 에외를 던집니다.
     *
     * @param email 체크할 이메일 주소
     * @throws EmailDuplicatedException 이메일이 이미 존재하는 경우 발생하는 예외
     */
    public void checkDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicatedException(
                    messageSource.getMessage("duplicated.user.email", null,
                            Locale.getDefault()));
        }
    }

    /**
     * 주어진 사용자 ID로 사용자를 검색합니다.
     * <p>
     * 사용자를 찾을 수 없는 경우 예외를 던집니다.
     *
     * @param userId 검색할 사용자 ID. 필수 값입니다.
     * @return 주어진 ID에 해당하는 사용자
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 발생하는 예외
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        messageSource.getMessage("not.found.user", null,
                                Locale.getDefault())));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
