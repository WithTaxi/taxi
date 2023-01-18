package com.withtaxi.taxi.service;

import com.withtaxi.taxi.model.ChatRoom;
import com.withtaxi.taxi.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private Map<String, ChatRoom> chatRooms;
    private final ChatRepository chatRepository;


    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }
    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = new ChatRoom().create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRepository.save(chatRoom);
    }

    //채팅방 조회
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    // 인원 +1
    public void plusUserCnt(String roomId) {
        ChatRoom room = chatRooms.get(roomId);
        room.setUserCount(room.getUserCount()+1);
        System.out.println("room Id: "+ roomId +" 서비스 유저 수: "+room.getUserCount()); // test 용도

    }
    // 인원 -1
    public void minusUserCnt(String roomId) {
        ChatRoom room = chatRooms.get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }
    // 임시 채팅방 찾기
    public ChatRoom findRoom(String name){
        return chatRooms.get(name);
    }

    //
    public String addUser(String roomId, String userName){
        ChatRoom room = chatRooms.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        room.getUserlist().put(userUUID,userName);
        return userUUID;
    }

    public void delUser(String roomId, String userUUID){
        ChatRoom room = chatRooms.get(roomId);
        room.getUserlist().remove(userUUID);
    }
    public String getUserName(String roomId, String userUUID){
        ChatRoom room = chatRooms.get(roomId);
        return room.getUserlist().get(userUUID);
    }

}