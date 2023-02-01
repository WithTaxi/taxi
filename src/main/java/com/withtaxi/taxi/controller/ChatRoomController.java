package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.model.ChatRoom;
import com.withtaxi.taxi.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// 제발
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }


    // 특정 채팅방 정보
    @GetMapping("/rooms/{roomId}")
    @ResponseBody
    public ChatRoom roomFind(@PathVariable String roomId){
        return chatService.findByRoomId(roomId);
    }

    // 채팅방 삭제
    @GetMapping("/room/delete/{roomId}")
    public void deleteRoom(@PathVariable String roomId){
        chatService.deleteRoom(roomId);
    }
}