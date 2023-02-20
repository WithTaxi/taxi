package com.withtaxi.taxi.service;

import com.withtaxi.taxi.model.ChatRoom;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.ChatRepository;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.*;


@Service
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
    public ChatRoom createRoom(String name,String userId) {
        ChatRoom chatRoom = new ChatRoom().create(name,userId);
        chatRooms.put(chatRoom.getRoomId(),chatRoom);
        return chatRepository.save(chatRoom);
    }

    // 사용자 목록에 사용자 추가
    public String addUser(String roomId, String userName) {
        ChatRoom room = chatRepository.findByRoomId(roomId);
        ChatRoom room1 = chatRooms.get(roomId);
        if (room != null) {
            String userUUID = UUID.randomUUID().toString();
            room.getUserlist().put(userUUID, userName);
            room1.getUserlist().put(userUUID, userName);
            return userUUID;
        } else {
            return roomId;
        }
    }


    // jpa 채팅방 찾기
    public ChatRoom findByRoomId(String roomId){
        return chatRepository.findByRoomId(roomId);
    }

    // 채팅방 삭제
    public void deleteRoom(String roomId){
        ChatRoom chatRoom = chatRepository.findByRoomId(roomId);
        ChatRoom room1 = chatRooms.get(roomId);
        chatRepository.delete(chatRoom);
        chatRooms.remove(room1);
    }

    //채팅방 조회
    public List<ChatRoom> findAllRoom(){
        return chatRepository.findAll();
    }


    //채팅방 조회
    public List<ChatRoom> findAllRoom1() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);


        return result;
    }

    public void addUserList(String roomId,String userId) {
        ChatRoom room = chatRooms.get(roomId);
//        room.getLiveUserList().add(userIds);
        room.getUserlist().put(userId,"hi");
        chatRepository.save(room);
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



    // 사용자 목록에서 사용자 삭제
    public void removeUser(String roomId, String userUUID){
        ChatRoom room = chatRepository.findByRoomId(roomId);
        ChatRoom room1 = chatRooms.get(roomId);

        if(room != null){
            room.getUserlist().remove(userUUID);
            room1.getUserlist().remove(userUUID);
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