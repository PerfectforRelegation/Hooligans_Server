package com.example.hooligan01.chatController;

import com.example.hooligan01.chatDto.ChatMessage;
import com.example.hooligan01.pubsub.RedisPublisher;
import com.example.hooligan01.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    // webSocket 에서의 WebSocketHandler 역할 대체

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    // "/pub/chat/message"로 들어오는 메시징 처리
    // @MessageMapping("/chat/message/{roomId}")        ->  @DestinationVariable
    // @SendTo("/sub/chat/{roomId}")
    @MessageMapping("/chat/message")
    public void message(@Payload ChatMessage chatMessage) {
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatRoomRepository.enterChatRoom(chatMessage.getRoomId());
            chatRoomRepository.plusUserCount(chatMessage.getRoomId());
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다!");
            System.out.println(chatMessage.getMessage().toString());
        }
        if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatRoomRepository.minusUserCount(chatMessage.getRoomId());
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장하셨습니다!");
            System.out.println(chatMessage.getMessage().toString());
        }

        // WebSocket 에 발행된 메시지를 redis 로 발행함(publish)
        redisPublisher.publish(chatMessage.getRoomId(), chatMessage);
    }

}