package com.project5s.IDEproject.domain.dto;

public record UserResetRequest(String username, String password, String newPassword) {
}
