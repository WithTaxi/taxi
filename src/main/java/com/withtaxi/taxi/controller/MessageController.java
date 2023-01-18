package com.withtaxi.taxi.controller;


import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Controller
@RequiredArgsConstructor
@Slf4j
@Data
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;


    @Autowired
    ChatService service;

    @MessageMapping("/chat/message")
    public void enterUser(User user, SimpMessageHeaderAccessor headerAccessor){


        if (User.MessageType.ENTER.equals(user.getType())) {
            service.plusUserCnt(user.getRoomId());
            String userUUID = service.addUser(user.getRoomId(), user.getSender());

            headerAccessor.getSessionAttributes().put("userUUID",userUUID);
            headerAccessor.getSessionAttributes().put("roomId",user.getRoomId());
            user.setMessage(user.getSender() + " 님이 입장하셨습니다");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+user.getRoomId(),user);
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event){

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        service.minusUserCnt(roomId);
        String username = service.getUserName(roomId,userUUID);
        service.delUser(roomId, userUUID);

        if (username != null ){
            User user = User.builder()
                    .type(User.MessageType.LEAVE)
                    .nickName(username)
                    .message(username + " 님 퇴장")
                    .build();
            sendingOperations.convertAndSend("/topic/chat/room/" + roomId,user);
        }


    }

}