package com.project5s.IDEproject.service;

import com.project5s.IDEproject.domain.Users;
import com.project5s.IDEproject.domain.dto.UserResetRequest;
import com.project5s.IDEproject.exception.AppException;
import com.project5s.IDEproject.exception.ErrorCode;
import com.project5s.IDEproject.repository.UserRepository;
import com.project5s.IDEproject.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret")
    private String key;
    private Long expriredTimeMs = 1000 * 60 * 60l;

    public String join(String userName, String password) {

        // userName 중복 check
        userRepository.findByUserName(userName)
                .ifPresent(users -> {throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "은(는) 이미 존재합니다.");
                });

        // 저장
        Users users = Users.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .build();
        userRepository.save(users);

        return "SUCCESS";
    }

    public String login(String userName, String password) {
        //userName 없음
        Users selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "이 없습니다."));

        //password 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 패스워드입니다.");
        }

        String token = JwtUtil.createToken(selectedUser.getUserName(), key, expriredTimeMs);

        // 앞에서 Exception 안났으면 토큰 발행
        return token;
    }

    @Transactional
    public void resetPassword(String userName, String password, String newPassword) {
        //userName 확인
        Users selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "이 없습니다."));

        //password 확인
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 패스워드입니다.");
        }

        selectedUser.updatePassword(encoder.encode(newPassword));
    }
}
