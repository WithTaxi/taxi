package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/join/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    String mailConfirm(@RequestParam("email") String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);

        return code;
    }
}
