package com.withtaxi.taxi.service;


import com.withtaxi.taxi.config.auth.PrincipalDetails;
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


    int checkPassword(String password, PrincipalDetails principalDetails);

    int modifyUserPassword(String password, PrincipalDetails principalDetails);

    int modifyUserInformation(PrincipalDetails principalDetails, User user);
}

