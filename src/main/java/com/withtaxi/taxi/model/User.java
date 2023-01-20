package com.withtaxi.taxi.model;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    public enum MessageType{
        ENTER, TALK, LEAVE
    }
    @Id
    private String userId;
    private String password;
    private String name; // 성명 적는 곳
    private String nickName; // 중복여부 파악해야할듯? // 채팅방 닉네임
    private String sex;
    private String mobile; // 모바일 인증이 될까?
    private String birthday;
    private String email; // 이메일인증 구현
    private String university; // 텍스트박스로 선택할 수 있게

    private String provider; // sns 종류
    private String providerId; // sns 아이디

    private MessageType type; // 메세지 타입
    private String roomId;    //채팅방 ID
    private String message; // 메세지 내용
    private String sender;


//    @CreationTimestamp 복구 예정
//    private Timestamp createDate; // 회원가입 날짜

    @Builder
    public User(String userId,
                String password,
                String name,
                String nickName,
                String sex,
                String mobile,
                String birthday,
                String email,
                String university,
                String provider,
                String providerId){
// 복구예정               Timestamp createDate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.sex = sex;
        this.mobile = mobile;
        this.birthday = birthday;
        this.email = email;
        this.university = university;
        this.provider = provider;
        this.providerId = providerId;
//        this.createDate = createDate; 복구예정
    }
}

