package com.withtaxi.taxi.repository;

import com.withtaxi.taxi.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends JpaRepository<ChatRoom, String> {
    ChatRoom findByRoomId(String roomId);

}
