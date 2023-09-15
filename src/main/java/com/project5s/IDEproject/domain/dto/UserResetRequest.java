package com.project5s.IDEproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResetRequest {
    private String userName;
    private String password;
    private String newPassword;
}
