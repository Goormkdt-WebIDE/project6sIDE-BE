package com.project5s.IDEproject.controller;

import com.project5s.IDEproject.domain.ChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Tag(name = "채팅 API")
public class ChatController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    @Operation(summary = "채팅 메시지 전송")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    @Operation(summary = "채팅방 입장")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}

