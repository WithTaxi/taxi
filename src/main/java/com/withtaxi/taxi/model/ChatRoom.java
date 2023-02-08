package com.withtaxi.taxi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    private String roomId;
    private String roomName;
    private String host_id; // 개설자 id
    private long userCount; // 채팅방 실시간 인원수
    private long maxUserCnt = 4; // 최대 인원


    @Transient
    private HashMap<String, String> userlist = new HashMap<String, String>();

    public ChatRoom create(String name,String userId) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name ;
        room.host_id = userId;
        return room;
    }

}