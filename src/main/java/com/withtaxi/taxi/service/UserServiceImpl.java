package com.withtaxi.taxi.service;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    public int checkPassword(String password, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String reqPassword = password;

        if (!passwordEncoder.matches(reqPassword, principalDetails.getPassword())) {
            return 0;
        }
        return 1;
    }

    @Override
    @Transactional
    public int modifyUserPassword(String password, PrincipalDetails principalDetails) {

        User user = principalDetails.getUser();

        String encPassword = passwordEncoder.encode(password);
        user.setPassword(encPassword);


        userRepository.save(user);

        return 1;
    }
}
