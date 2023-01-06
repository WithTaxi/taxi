package com.withtaxi.taxi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.UUID;
//ChatRoom
@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    private Long id;

    private String roomId;
    private String roomName;
    private long userCount; // 채팅방 인원수
    private long maxUserCnt = 4; // 최대 인원



    @Transient
    @Convert
    private HashMap<String, String> userlist = new HashMap<String, String>();

    public ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;

        return room;
    }

}