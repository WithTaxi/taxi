package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    /***
     * 일반 로그인
     * @return
     */
    @GetMapping("/loginForm") // SecurityConfig 파일을 작성하면 스프링시큐리티의 자동 login 페이지 사용 불가능
    public String loginForm() {
        return "loginForm";
    }

    /***
     * 회원가입 홈페이지 이동
     * @return
     */
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }
}