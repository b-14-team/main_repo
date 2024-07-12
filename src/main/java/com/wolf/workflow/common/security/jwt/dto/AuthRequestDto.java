package com.wolf.workflow.common.security.jwt.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {
    private String email;
    private String password;
}