package com.example.hooligan01.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ChatMessage implements Serializable {

    public enum MessageType {
        ENTER, TALK;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

//    @JsonCreator
//    public ChatMessage(
//            @JsonProperty("type") MessageType type,
//            @JsonProperty("roomId") String roomId,
//            @JsonProperty("type") String sender,
//            @JsonProperty("type") String message) {
//
//        this.type = type;
//        this.roomId = roomId;
//        this.sender = sender;
//        this.message = message;
//    }
}
