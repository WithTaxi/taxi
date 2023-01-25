package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.UserRepository;
import com.withtaxi.taxi.service.EmailService;
import com.withtaxi.taxi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/mailConfirm")
    String mailConfirm(@RequestParam("email") String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);

        return code;
    }

    @PostMapping("/issuedTemporaryPassword")
    public int issueTemporaryPassword(@RequestParam("userId") String userId) throws Exception {

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return 0;
        }

        String temporaryPassword = emailService.sendSimpleMessage(user.getEmail());


        String encPassword = passwordEncoder.encode(temporaryPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
        return 1;
    }
}
