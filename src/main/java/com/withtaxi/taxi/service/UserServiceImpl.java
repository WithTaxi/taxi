package com.withtaxi.taxi.service;

import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User findId(String name, String email) {
        User result = null;

        try {
            result = userRepository.findByNameAndEmail(name, email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    @Transactional
    public void removeUser(String userId) {
        userRepository.deleteByUserId(userId);
    }

    @Override
    public String findEmail(String userId) {
        User user = userRepository.findByUserId(userId);
        return user.getEmail();
    }

    @Override
    public void updatePassword(String userId) {
        User user = userRepository.findByUserId(userId);
        String rawPassword = user.getPassword();

        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
    }
}
