package com.wolf.workflow.user.controller;

import com.wolf.workflow.common.globalresponse.ApiResponse;
import com.wolf.workflow.common.security.AuthenticationUser;
import com.wolf.workflow.user.dto.request.UserResignRequestDto;
import com.wolf.workflow.user.dto.request.UserSignupRequestDto;
import com.wolf.workflow.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param requestDto 회원가입 요청 데이터를 담고 있는 dto
     * @NotBlank email, password, nickname
     */
    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> userSignup(
            @Valid @RequestBody UserSignupRequestDto requestDto
    ) {
        userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("회원가입 되었습니다."));
    }

    /**
     * 회원탈퇴
     *
     * @param requestDto 회원탈퇴 요청 데이터를 담고 있는 dto
     * @NotBlank password
     */
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/resign")
    public ResponseEntity<ApiResponse<String>> userResign(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @Valid @RequestBody UserResignRequestDto requestDto
    ) {
        userService.deleteUser(authenticationUser.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("회원탈퇴 되었습니다."));
    }

}
