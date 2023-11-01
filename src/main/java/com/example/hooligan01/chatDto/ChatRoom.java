package com.example.hooligan01.chatDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;
    private Long count;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        chatRoom.count = 0L;
        return chatRoom;
    }

    public Long getCount() {
        return count;
    }

    public void plusCount() {
        count++;
    }

    public void minusCount() { count--; }
}
