package com.withtaxi.taxi.service;


import com.withtaxi.taxi.model.User;

public interface UserService {

    /***
     * 아이디 찾기
     * @param name
     * @param email
     * @return
     */
    User findId(String name, String email);

    void removeUser(String userId);

    String findEmail(String userId);

    void updatePassword(String userId);
}

