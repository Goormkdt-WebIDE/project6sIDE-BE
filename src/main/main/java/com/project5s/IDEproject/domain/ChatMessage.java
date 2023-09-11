package com.project5s.IDEproject.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatMessage {
    private String sender;
    private String content;
    private String date;
    private MessageType type;
}
