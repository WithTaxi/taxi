package com.withtaxi.taxi.service;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.model.dto.UserRequestDto;
import com.withtaxi.taxi.model.dto.UserResponseDto;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    public String findId(String name, String email) {
        User user = null;

        try {
            user = userRepository.findByNameAndEmail(name, email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UserResponseDto(user).getUserId();
    }

    @Override
    @Transactional
    public int removeUser(String userId) {
        userRepository.deleteByUserId(userId);

        return 1;
    }

    @Override
    public int checkPassword(String password, PrincipalDetails principalDetails) {
        String reqPassword = password;

        if (!passwordEncoder.matches(reqPassword, principalDetails.getUser().getPassword())) {
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
    @Override
    public int modifyUserInformation(PrincipalDetails principalDetails, UserRequestDto user) {
        User modifyUser = principalDetails.getUser();

        modifyUser.setNickName(user.getNickName());
        modifyUser.setMobile(user.getMobile());
        modifyUser.setEmail(user.getEmail());
        modifyUser.setUniversity(user.getUniversity());

        userRepository.save(modifyUser);

        return 1;
    }
}
