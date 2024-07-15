package com.wolf.workflow.user.service.dto;

import com.wolf.workflow.user.dto.request.UserSignupRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupUserDto {

    private String email;

    private String password;

    private String nickName;

    private String description;

    public static SignupUserDto of(UserSignupRequestDto requestDto, String encodePassword) {
        return SignupUserDto.builder()
                .email(requestDto.getEmail())
                .password(encodePassword)
                .nickName(requestDto.getNickName())
                .description(requestDto.getDescription())
                .build();
    }

}
