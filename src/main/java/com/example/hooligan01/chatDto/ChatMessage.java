package com.example.hooligan01.chatDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ChatMessage {

    public enum MessageType {
        ENTER, QUIT, TALK;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private Long userCount;

}
