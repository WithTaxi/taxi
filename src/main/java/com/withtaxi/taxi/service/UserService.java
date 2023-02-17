package com.withtaxi.taxi.service;


import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.model.dto.UserRequestDto;
import com.withtaxi.taxi.model.dto.UserResponseDto;

public interface UserService {

    /***
     * 아이디 찾기
     * @param name
     * @param email
     * @return
     */
    String findId(String name, String email);

    int removeUser(String userId);


    int checkPassword(String password, PrincipalDetails principalDetails);

    int modifyUserPassword(String password, PrincipalDetails principalDetails);

    int modifyUserInformation(PrincipalDetails principalDetails, UserRequestDto user);
}

