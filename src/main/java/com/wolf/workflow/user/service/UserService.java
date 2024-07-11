package com.wolf.workflow.user.service;

import com.wolf.workflow.common.exception.DuplicatedEmailException;
import com.wolf.workflow.common.exception.MismatchPasswordException;
import com.wolf.workflow.common.exception.NotFoundUserException;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.dto.request.UserResignRequestDto;
import com.wolf.workflow.user.dto.request.UserSignupRequestDto;
import com.wolf.workflow.user.entity.User;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;
    private final MessageSource messageSource;

    /**
     * 사용자를 생성합니다.
     * <p>
     * 사용자 이메일의 중복을 체크하고, 중복이 없으면 새로운 사용자를 생성합니다.
     *
     * @param requestDto 회원가입 요청 데이터를 담고 있는 UserSignupRequestDto 객체. 필수 값입니다.
     * @throws DuplicatedEmailException 이메일이 이미 존재하는 경우 발생하는 예외
     */
    @Transactional
    public void createUser(UserSignupRequestDto requestDto) {
        userAdapter.checkDuplicatedEmail(requestDto.getEmail());

        User user = User.createUser(requestDto);
        userAdapter.createUser(user);
    }

    /**
     * 사용자를 삭제합니다. 이 메서드는 사용자 ID와 비밀번호를 기반으로 사용자를 삭제합니다. 비밀번호가 일치하지 않는 경우
     * {@code PasswordMismatchException}을 던집니다.
     *
     * @param requestDto 회원탈퇴 요청 데이터를 담고 있는 UserResignRequestDto 객체. 필수 값입니다.
     * @throws NotFoundUserException     사용자를 찾을 수 없는 경우 발생하는 예외
     * @throws MismatchPasswordException 비밀번호가 일치하지 않는 경우 발생하는 예외
     */
    @Transactional
    public void deleteUser(UserResignRequestDto requestDto) {
        User user = userAdapter.getUserById(1L); //TODO
        if (!Objects.equals(requestDto.getPassword(), user.getPassword())){
            throw new MismatchPasswordException(
                    messageSource.getMessage("mismatch.password", null, Locale.getDefault()));
        }
        user.updateStatus();
    }
}
