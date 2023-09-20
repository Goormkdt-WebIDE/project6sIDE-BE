package com.project5s.IDEproject.dto;

public record UserResetRequest(String email, String password, String newPassword) {
}
