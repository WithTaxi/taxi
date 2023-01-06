package com.withtaxi.taxi.model;

import lombok.*;
//ChatMessage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    private MessageType type;

    private String roomId;    //채팅방 ID

    private String sender;    //보내는 사람

    private String message;    //내용

}
