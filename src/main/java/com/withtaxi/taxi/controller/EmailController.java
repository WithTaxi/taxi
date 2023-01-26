package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    String mailConfirm(@RequestParam("email") String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);

        return code;
    }

    @PostMapping("/issueTemporaryPassword")
    int issueTemporaryPassword(@RequestParam("userId") String userId) throws Exception {
        return emailService.issuedTemporaryPassword(userId);
    }
}
