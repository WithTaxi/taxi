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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 아이디가 이거롤 쓰면 안될거같아
    private String username; // 아이디임 (JPA 개어려워서 어쩔수없이 변수명 이난리고 사실상 유저 아이디 적는곳)
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

