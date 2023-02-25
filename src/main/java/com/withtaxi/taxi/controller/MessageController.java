package com.withtaxi.taxi.controller;


import com.withtaxi.taxi.model.ChatMessage;
import com.withtaxi.taxi.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Data
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService service;
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/message")
    public void enterUser(ChatMessage chat, SimpMessageHeaderAccessor headerAccessor){


        if (ChatMessage.MessageType.ENTER.equals(chat.getType())) {

            String userUUID = UUID.randomUUID().toString();
            service.increaseUserCount(chat.getRoomId());

            headerAccessor.getSessionAttributes().put("userUUID",userUUID);
            headerAccessor.getSessionAttributes().put("roomId",chat.getRoomId());
            chat.setMessage(chat.getSender()+ " 님이 입장하셨습니다");
            chat.setSender("[알림]");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+chat.getRoomId(),chat);
    }


    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event){

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");


        String username = service.getUserName(roomId,userUUID);
        service.decreaseUserCount(roomId);

        if (username != null ){
            ChatMessage chatMessage = ChatMessage.builder()
                    .type(ChatMessage.MessageType.LEAVE)
                    .sender(username)
                    .message(username + " 님 퇴장")
                    .build();
            sendingOperations.convertAndSend("/topic/chat/room/" + roomId,chatMessage);
        }


    }

}