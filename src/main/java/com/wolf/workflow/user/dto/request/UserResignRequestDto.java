package com.wolf.workflow.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserResignRequestDto {

    @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
    private String password;
}
