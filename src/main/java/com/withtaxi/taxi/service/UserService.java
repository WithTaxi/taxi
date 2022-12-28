package com.withtaxi.taxi.service;


import com.withtaxi.taxi.model.User;

public interface UserService {

    public User findId(String name, String email);
}
