package com.project5s.IDEproject.controller;

import com.project5s.IDEproject.dto.UserJoinRequest;
import com.project5s.IDEproject.dto.UserLoginRequest;
import com.project5s.IDEproject.dto.UserResetRequest;
import com.project5s.IDEproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API")
    @PostMapping("/signUp")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.username(), dto.email(), dto.password());
        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
    }

    @Operation(summary = "로그인 API & ACCESS_TOKEN 발급")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto) {
        String token = userService.login(dto.email(), dto.password());
        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "비밀번호 리셋 API")
    @PostMapping("/resetPassword")
    public ResponseEntity<String> reset(@RequestBody UserResetRequest dto) {
        userService.resetPassword(dto.email(), dto.password(), dto.newPassword());
        return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
    }

    @Operation(summary = "데이터베이스 리셋")
    @GetMapping("/resetDB")
    public ResponseEntity<String> reset() {

        return ResponseEntity.ok().body("DB 리셋이 완료되었습니다.");
    }


}
