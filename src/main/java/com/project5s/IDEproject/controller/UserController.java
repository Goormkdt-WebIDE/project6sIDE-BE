package com.project5s.IDEproject.controller;

import com.project5s.IDEproject.domain.dto.UserJoinRequest;
import com.project5s.IDEproject.domain.dto.UserLoginRequest;
import com.project5s.IDEproject.domain.dto.UserResetRequest;
import com.project5s.IDEproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.getUserName(), dto.getPassword());
        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> log(@RequestBody UserLoginRequest dto) {
        String token = userService.login(dto.getUserName(), dto.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestBody UserResetRequest dto) {
        userService.resetPassword(dto.getUserName(), dto.getPassword(), dto.getNewPassword());
        return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
    }
}
