package com.withtaxi.taxi.model;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
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
    @CreationTimestamp
    private Timestamp createDate; // 회원가입 날짜
}

