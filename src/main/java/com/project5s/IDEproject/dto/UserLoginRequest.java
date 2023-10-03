package com.project5s.IDEproject.dto;

import javax.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank(message = "이메일은 필수 입력 값입니다.")
                               String email,
                               @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
                               String password) {
}