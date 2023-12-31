package com.project5s.IDEproject.service;

import com.project5s.IDEproject.domain.User;
import com.project5s.IDEproject.exception.AppException;
import com.project5s.IDEproject.exception.ErrorCode;
import com.project5s.IDEproject.repository.UserRepository;
import com.project5s.IDEproject.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public String join(String username, String email, String password) {

        // username, email 중복 check
        userRepository.findByEmail(email)
                .ifPresent(user -> {throw new AppException(ErrorCode.USERNAME_DUPLICATED, email + "은(는) 이미 존재합니다.");
                });
        userRepository.findByUsername(username)
                .ifPresent(user -> {throw new AppException(ErrorCode.USERNAME_DUPLICATED, username + "은(는) 이미 존재합니다.");
                });
        // 저장

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(password))
                .build();
        userRepository.save(user);

        return "SUCCESS";
    }

    public String login(String email, String password) {
        //username 없음
        User selectedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, email + "이 없습니다."));

        //password 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 패스워드입니다.");
        }

        String token = jwtUtil.createToken(selectedUser.getEmail());
        // 앞에서 Exception 안났으면 토큰 발행
        return token;
    }

    @Transactional
    public void resetPassword(String email, String password, String newPassword) {
        //username 확인
        User selectedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, email + "이 없습니다."));

        //password 확인
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 패스워드입니다.");
        }

        selectedUser.updatePassword(encoder.encode(newPassword));
    }

    public void resetDB(){
        userRepository.deleteAll();
    }


}
