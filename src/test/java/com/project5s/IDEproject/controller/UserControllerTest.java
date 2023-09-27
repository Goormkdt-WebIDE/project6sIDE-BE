<<<<<<< Updated upstream
package com.project5s.IDEproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project5s.IDEproject.dto.UserJoinRequest;
import com.project5s.IDEproject.dto.UserLoginRequest;
import com.project5s.IDEproject.exception.AppException;
import com.project5s.IDEproject.exception.ErrorCode;
import com.project5s.IDEproject.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

//    @Test
//    @DisplayName("회원가입 성공")
//    @WithMockUser
//    void join() throws Exception {
//        String email = "spring@naver.com";
//        String password = "1e2d21";
//
//        mockMvc.perform(post("/api/v1/users/join")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, password))))
//                .andDo(print())
//                .andExpect(status().is4xxClientError());
//    }

//    @Test
//    @DisplayName("회원가입 실패 - username 중복")
//    @WithMockUser
//    void join_fail() throws Exception {
//        String userName = "spring";
//        String password = "1e2d21";
//
//        when(userService.join(any(), any()))
//                .thenThrow(new RuntimeException("중복된 userId 입니다."));
//
//        mockMvc.perform(post("/api/v1/users/join")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password))))
//                .andDo(print())
//                .andExpect(status().is4xxClientError());
//    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {
        String username = "spring";
        String password = "1e2d21";

        when(userService.login(any(), any()))
                .thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password))))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("로그인 실패 - userName 없음")
    @WithMockUser
    void login_fail1() throws Exception {
        String userName = "spring";
        String password = "1e2d21";

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("로그인 실패 - password 틀림")
    @WithMockUser
    void login_fail2() throws Exception {
        String userName = "spring";
        String password = "1e2d21";

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
=======
        package com.project5s.IDEproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project5s.IDEproject.dto.UserJoinRequest;
import com.project5s.IDEproject.dto.UserLoginRequest;
import com.project5s.IDEproject.exception.AppException;
import com.project5s.IDEproject.exception.ErrorCode;
import com.project5s.IDEproject.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

        @Autowired
        MockMvc mockMvc;

        @MockBean
        UserService userService;

        @Autowired
        ObjectMapper objectMapper;

        @Test
        @DisplayName("회원가입 성공")
        @WithMockUser
        void join() throws Exception {
            String username = "spring";
            String email = "asd@naver.com";
            String password = "1e2d21";

            mockMvc.perform(post("/api/users/join")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, email, password))))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("회원가입 실패 - username 중복")
        @WithMockUser
        void join_fail() throws Exception {
            String userName = "spring";
            String email = "asd@naver.com";
            String password = "1e2d21";

            when(userService.join(any(), any(), any()))
                    .thenThrow(new RuntimeException("중복된 userId 입니다."));

            mockMvc.perform(post("/api/users/join")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, email, password))))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("로그인 성공")
        @WithMockUser
        void login_success() throws Exception {
            String username = "spring";
            String password = "1e2d21";

            when(userService.login(any(), any()))
                    .thenReturn("token");

            mockMvc.perform(post("/api/users/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password))))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("로그인 실패 - userName 없음")
        @WithMockUser
        void login_fail1() throws Exception {
            String email = "asd@naver.com";
            String password = "1e2d21";

            when(userService.login(any(), any()))
                    .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));

            mockMvc.perform(post("/api/users/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password))))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("로그인 실패 - password 틀림")
        @WithMockUser
        void login_fail2() throws Exception {
            String email = "asd@naver.com";
            String password = "1e2d21";

            when(userService.login(any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));

            mockMvc.perform(post("/api/users/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password))))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }