package com.project5s.IDEproject.controller;

import com.project5s.IDEproject.domain.dto.UserJoinRequest;
import com.project5s.IDEproject.domain.dto.UserLoginRequest;
import com.project5s.IDEproject.domain.dto.UserResetRequest;
import com.project5s.IDEproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API")
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.username(), dto.password());
        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
    }

    @Operation(summary = "로그인 API & ACCESS_TOKEN 발급")
    @PostMapping("/login")
    public ResponseEntity<String> log(@RequestBody UserLoginRequest dto) {
        String token = userService.login(dto.username(), dto.password());
        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "비밀번호 리셋 API")
    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestBody UserResetRequest dto) {
        userService.resetPassword(dto.username(), dto.password(), dto.newPassword());
        return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
    }
}
