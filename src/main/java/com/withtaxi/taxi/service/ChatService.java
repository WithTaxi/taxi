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
    public ChatRoom createRoom(String name,String host) {
        ChatRoom chatRoom = new ChatRoom().create(name,host);
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
        if (room != null) {
            room.setUserCount(room.getUserCount() + 1);
            chatRepository.save(room);
        } else {
            System.out.println(" ");
        }
    }
    //사용자 수 감소
    public void decreaseUserCount(String roomId) {
        ChatRoom room = chatRepository.findByRoomId(roomId);
        if (room != null) {
            room.setUserCount(room.getUserCount() - 1);
            chatRepository.save(room);
        } else {
            System.out.println(" ");
        }

    }

    // 사용자 목록에 사용자 추가
    public String addUser(String roomId, String userName) {
        ChatRoom room = chatRepository.findByRoomId(roomId);
        if (room != null) {
            String userUUID = UUID.randomUUID().toString();
            room.getUserlist().put(userUUID, userName);
            return userUUID;
        } else {
             return roomId;
        }
    }

    // 사용자 목록에서 사용자 삭제
    public void removeUser(String roomId, String userUUID){
        ChatRoom room = chatRepository.findByRoomId(roomId);
        if(room != null){
            room.getUserlist().remove(userUUID);
        }
    }
    // 사용자 검색
    public String getUserName(String roomId, String userUUID){
        ChatRoom room = chatRepository.findByRoomId(roomId);
        if (room == null) {
            return null;
        }
        Map<String, String> userlist = room.getUserlist();
        if (userlist == null || !userlist.containsKey(userUUID)) {
            return null;
        }
        return userlist.get(userUUID);
    }

}