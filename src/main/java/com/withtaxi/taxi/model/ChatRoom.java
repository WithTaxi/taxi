package com.withtaxi.taxi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.*;


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
    private long maxUserCnt = 4; // 최대 인원ㅈ


    @Transient
    @Convert
    private HashMap<String, String> userlist = new HashMap<String, String>();



    public ChatRoom create(String name,String userId) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name ;
        room.host_id = userId;
        return room;
    }

}