package com.withtaxi.taxi.service;

import com.withtaxi.taxi.model.ChatRoom;
import com.withtaxi.taxi.repository.ChatRepository;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = new ChatRoom().create(name);
        return chatRepository.save(chatRoom);
    }
    // jpa 채팅방 찾기
    public ChatRoom findByRoomId(String roomId){
        return chatRepository.findByRoomId(roomId);
    }

    // 채팅방 삭제
    public void deleteRoom(String roomId){
        ChatRoom chatRoom = chatRepository.findByRoomId(roomId);
        chatRepository.delete(chatRoom);
    }

    //채팅방 조회
    public List<ChatRoom> findAllRoom(){
        return chatRepository.findAll();
    }

    //사용자 수 증가
    public void increaseUserCount(String roomId) {
        ChatRoom room = chatRepository.findByRoomId(roomId);
        room.setUserCount(room.getUserCount() + 1);
        chatRepository.save(room);
    }
    //사용자 수 감소
    public void decreaseUserCount(String roomId) {
        ChatRoom room = chatRepository.findByRoomId(roomId);
        room.setUserCount(room.getUserCount() - 1);
        chatRepository.save(room);
    }

    // 사용자 목록에 사용자 추가
    public String addUser(String roomId, String userName){
        ChatRoom room = chatRepository.findByRoomId(roomId);
        String userUUID = UUID.randomUUID().toString();
        room.getUserlist().put(userUUID,userName);
        return userUUID;
    }
    // 사용자 목록에서 사용자 삭제
    public void delUser(String roomId, String userUUID){
        ChatRoom room = chatRepository.findByRoomId(roomId);
        room.getUserlist().remove(userUUID);
        System.out.println(room.getUserlist().size());

    }
    // 사용자 검색
    public String getUserName(String roomId, String userUUID){
        ChatRoom room = chatRepository.findByRoomId(roomId);
        return room.getUserlist().get(userUUID);
    }

}