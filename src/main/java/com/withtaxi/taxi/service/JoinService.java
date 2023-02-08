package com.withtaxi.taxi.service;

import com.withtaxi.taxi.model.User;


public interface JoinService {


    User registerUser(User user);
    boolean checkUserIdDuplicate(String userId);

    boolean checkNickNameDuplicate(String nickName);

    boolean checkEmailDuplicate(String email);
}
