package com.example.hooligan01.chatController;

import com.example.hooligan01.chatDto.ChatRoom;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 리스트
    @GetMapping("/room")
    public List<ChatRoom> rooms() {
        return chatRoomRepository.findAllRoom();
    }

    @GetMapping("/room/{roomId}")
    public ChatRoom chatRoom(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomRepository.createChatRoom(chatRoom.getName());
    }

    // 채팅방 삭제
    @DeleteMapping("/room/{roomId}")
    public boolean deleteChatRoom(@PathVariable String roomId) {
        return chatRoomRepository.deleteChatRoom(roomId);
    }
}
