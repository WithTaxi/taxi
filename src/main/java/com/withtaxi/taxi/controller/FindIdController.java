package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.UserRepository;
import com.withtaxi.taxi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class FindIdController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/findIdForm")
    public String findIdForm() {
        return "findIdForm";
    }

    @GetMapping("/findId")
    public @ResponseBody String findId(@RequestParam("name") String name, @RequestParam("email") String email) {
        User result = userService.findId(name, email);
        return result.getUserId();
    }
}
