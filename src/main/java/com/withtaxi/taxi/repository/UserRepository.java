package com.withtaxi.taxi.repository;


import com.withtaxi.taxi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
}

