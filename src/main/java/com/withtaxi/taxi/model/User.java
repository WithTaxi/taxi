package com.withtaxi.taxi.model;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    private String password;
    private String name; // 성명 적는 곳
    private String nickName; // 중복여부 파악해야할듯?
    private String sex;
    private String mobile; // 모바일 인증이 될까?
    private String birthday;
    private String email; // 이메일인증 구현
    private String university; // 텍스트박스로 선택할 수 있게

    private String provider; // sns 종류
    private String providerId; // sns 아이디

    @CreationTimestamp
    private Timestamp createDate; // 회원가입 날짜

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
                String providerId,
                Timestamp createDate) {
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
        this.createDate = createDate;
    }
}

