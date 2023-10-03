package com.project5s.IDEproject.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UserResetRequest(@NotBlank(message = "이메일은 필수 입력 값입니다.")
                               String email,
                               @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
                               String password,
                               @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
                               @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
                               String newPassword) {
}